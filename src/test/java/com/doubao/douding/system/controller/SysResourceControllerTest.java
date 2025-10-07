package com.doubao.douding.system.controller;

import com.doubao.douding.system.dto.SysResourceDTO;
import com.doubao.douding.system.entity.SysResource;
import com.doubao.douding.system.enums.SysResourceEnum;
import com.doubao.douding.system.service.SysResourceService;
import io.ebean.DB;
import io.ebean.test.UserContext;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@WithMockUser(roles = {"ADMIN"})
public class SysResourceControllerTest extends BaseControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private SysResourceService sysResourceService;

    private SysResourceDTO sysResourceDTO;

    @BeforeEach
    public void setUp() {
        UserContext.setUserId("test");
        sysResourceDTO = SysResourceDTO.builder()
                                       .resourceName("User Management")
                                       .resourceType(SysResourceEnum.SysResourceTypeEnum.MENU.getCode())
                                       .url("/sysRole/page")
                                       .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                       .build();
    }

    @AfterEach
    public void cleanUp() {
        DB.truncate(SysResource.class);
    }

    @Nested
    class Add {
        @SneakyThrows
        @Test
        public void create_withValidData_shouldBeOK() {
            postAction(mockMvc, "/sysResource/add", sysResourceDTO)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.resourceName").value(sysResourceDTO.getResourceName()))
                .andExpect(jsonPath("$.url").value(sysResourceDTO.getUrl()))
                .andExpect(jsonPath("$.resourceType").value(SysResourceEnum.SysResourceTypeEnum.MENU.getCode()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        }
    }

    // update
    // update with id exist
    // update with id not exist

    @Nested
    class Update {
        @Test
        @SneakyThrows
        public void update_withValidData_shouldBeOK() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            sysResourceDTO.setUrl("/sysRole/page/update");
            sysResourceDTO.setResourceName("User Management new");

            putAction(mockMvc, "/sysResource/update", sysResourceDTO)
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.url").value("/sysRole/page/update"))
                .andExpect(jsonPath("$.resourceName").value("User Management new"));
        }

        @Test
        @SneakyThrows
        public void update_withInvalidData_shouldBeBadRequest() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            sysResourceDTO.setResourceName(null);

            putAction(mockMvc, "/sysResource/update", sysResourceDTO)
                .andExpect(status().isInternalServerError());
        }

        @Test
        @SneakyThrows
        public void update_withIdNotExist_shouldBeNotFound() {
            sysResourceDTO.setId(123456789L);
            putAction(mockMvc, "/sysResource/update", sysResourceDTO)
                .andExpect(status().isNotFound());


        }
    }

    @Nested
    class Delete {

        @Test
        @SneakyThrows
        public void delete_withIdExist_shouldBeOK() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            deleteAction(mockMvc, "/sysResource/delete/" + sysResourceDTO.getId())
                .andExpect(status().isOk());

        }

        @Test
        @SneakyThrows
        public void delete_withIdNotExist_shouldBeNotFound() {
            deleteAction(mockMvc, "/sysResource/delete/999")
                .andExpect(status().isOk());

        }
    }

    @Nested
    class Get{

        @Test
        @SneakyThrows
        public void get_withExistingId_shouldBeOK() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);

            getAction(mockMvc, "/sysResource/" + sysResourceDTO.getId())
                .andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        public void get_withNonExistingId_shouldBeNotFound() {
            getAction(mockMvc, "/sysResource/999")
                .andExpect(status().isNotFound());

        }

        @Test
        @SneakyThrows
        public void search_withValidData_shouldBeOK() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            sysResourceDTO.setPageIndex(1);
            sysResourceDTO.setPageSize(10);

            postAction(mockMvc, "/sysResource/page", sysResourceDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.totalCount").value(1));

        }

        @Test
        @SneakyThrows
        public void search_withInvalidData_shouldNotFound() {
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            sysResourceDTO.setResourceName("Resource Management");
            sysResourceDTO.setPageIndex(1);
            sysResourceDTO.setPageSize(10);

            postAction(mockMvc, "/sysResource/page", sysResourceDTO)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list").isArray())
                .andExpect(jsonPath("$.totalCount").value(0));

        }

    }
}
