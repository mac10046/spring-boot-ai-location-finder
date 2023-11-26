package com.sls.PostalCodeSearcher.controller;


import com.sls.PostalCodeSearcher.entity.PostalCode;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postalcode")
public class PostalCodeController {

    private final AiClient aiClient;


    public PostalCodeController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/search")
    public PostalCode getPostalCode(@Param("postalCode") String postalCode) {
        BeanOutputParser<PostalCode> parser = new BeanOutputParser<>(PostalCode.class);

        String promptString = "can you help me find country, state, city and area details for the postal code {postalCode}?" +
                "{format}";
        PromptTemplate template = new PromptTemplate(promptString);
        template.add("postalCode", postalCode);
        template.add("format", parser.getFormat());
        template.setOutputParser(parser);

        Prompt prompt = template.create();
        AiResponse aiResponse = aiClient.generate(prompt);
        String text = aiResponse.getGeneration().getText();

        PostalCode postalCodeObj = parser.parse(text);

        //TODO: Save the entity to the database and reuse when the same request is received to save Open API calls
        return postalCodeObj;
    }
}
