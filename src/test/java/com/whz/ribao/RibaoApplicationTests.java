package com.whz.ribao;

import com.whz.ribao.web.RibaoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RibaoApplicationTests {

    private MockMvc mvc;

	@Before
	public void contextLoads() {
        mvc = MockMvcBuilders.standaloneSetup(new RibaoController()).build();
	}

    @Test
    public void add() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post("/ribao/save")
                        .requestAttr("module", "控制中心")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
