package word.counter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import word.counter.exception.IllegalCharacterException;
import word.counter.model.Word;
import word.counter.service.WordCounterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WordCounterController.class)
public class WordCounterControllerTest {

    @MockBean
    private WordCounterService wordCounterService;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateWord() throws IllegalCharacterException, Exception {

        Word word = new Word("ashish");
        String json = mapper.writeValueAsString(word);

        mockMvc.perform(post("/api/wordadd").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    public void testGetWordCount() throws IllegalCharacterException, Exception {

        Word word = new Word("ashish");
        String json = mapper.writeValueAsString(word);
        long count = 10;
        Mockito.when(wordCounterService.countSimilarMeaningWords(ArgumentMatchers.any())).thenReturn(count);


        MvcResult requestResult = mockMvc.perform(post("/api/wordcount").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals("10" , result);
    }



}
