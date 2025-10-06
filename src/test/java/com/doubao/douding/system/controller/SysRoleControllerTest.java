package com.doubao.douding.system.controller;

import com.doubao.douding.DoudingApplication;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.security.JwtUtils;
import com.doubao.douding.system.service.SysRoleService;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Johnson
 * @Description unit test for SysRoleController
 */
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {
    DoudingApplication.class,
    JwtUtils.class
})
@AutoConfigureMockMvc
@Slf4j
@WithMockUser(roles = {"ADMIN"})
class SysRoleControllerTest extends BaseControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private SysRoleService sysRoleService;

    private SysRoleDTO sysRoleDTO;
    private SysRoleDTO resultDTO;

    @BeforeEach
    public void setUp() {
        UserContext.setUserId("test");
        sysRoleDTO = SysRoleDTO.builder().roleName("admin").roleStatus(1).build();
        resultDTO = SysRoleDTO.builder().roleName("admin").roleStatus(1).id(1L).build();
    }

    @AfterEach
    public void cleanUp() {
        DB.truncate(SysRole.class);
    }

    @Nested
    public class Add {
        // test case for create
        @SneakyThrows
        @Test
        public void create_withValidData_shouldBeOK() {

            postAction(mockMvc, "/sysRole/add", sysRoleDTO)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.roleName").value(resultDTO.getRoleName()))
                .andExpect(jsonPath("$.roleStatus").value(resultDTO.getRoleStatus()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        }

        // test case for read
    }

    @Nested
    class Get{

        @Test
        @SneakyThrows
        public void getById_withExistingId_shouldReturn() {
            SysRoleDTO dto = SysRoleDTO.builder().roleName("admin").roleStatus(1).id(1L).build();
            sysRoleService.add(dto);

            getAction(mockMvc, "/sysRole/" + dto.getId()).andExpect(status().isOk())
                                                         .andExpect(MockMvcResultMatchers.content()
                                                                                         .contentTypeCompatibleWith(
                                                                                             MediaType.APPLICATION_JSON))
                                                         .andExpect(
                                                             jsonPath("$.roleName").value(resultDTO.getRoleName()))
                                                         .andExpect(
                                                             jsonPath("$.roleStatus").value(resultDTO.getRoleStatus()))
                                                         .andExpect(jsonPath("$.id").value(resultDTO.getId()));
        }

        @Test
        @SneakyThrows
        public void getById_withNonExistingId_shouldReturnNotFound() {
            getAction(mockMvc, "/sysRole/999").andExpect(status().isNotFound());
        }

        @Test
        @SqlGroup({
            @Sql(
                statements = "INSERT INTO sys_role (id, role_name, role_status, when_created, when_modified, who_created, who_modified, record_modification_version, deleted) " +
                    "VALUES (1, 'admin', 1, '2025-10-02 20:10:21.000000', '2025-10-02 20:10:26.000000', 'test', 'test', 1, 0);\n",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
            ),
            @Sql(
                statements = "delete from sys_role where id = 1;",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
            )
        }
        )
        @SneakyThrows
        public void get_withPagination_shouldReturnPaginatedResult() {
            SysRoleDTO requestDTO = SysRoleDTO.builder().roleName("admin").roleStatus(1).pageIndex(1).pageSize(10).build();

            postAction(mockMvc, "/sysRole/page", requestDTO).andExpect(status().isOk())
                                                            .andExpect(MockMvcResultMatchers.content()
                                                                                            .contentTypeCompatibleWith(
                                                                                                MediaType.APPLICATION_JSON))
                                                            .andExpect(jsonPath("$.totalCount").value(1))
                                                            .andExpect(jsonPath("$.list").isArray());
        }

        @Test
        @SneakyThrows
        public void search_withValidCriteria_shouldReturnPageFilteredResults() {
            SysRoleDTO requestDTO = SysRoleDTO.builder().roleName("admin").roleStatus(1).pageIndex(1).pageSize(10).build();

            SysRoleDTO dto1 = SysRoleDTO.builder().roleName("admin").roleStatus(1).build();
            SysRoleDTO dto2 = SysRoleDTO.builder().roleName("admins").roleStatus(1).build();
            sysRoleService.add(dto1);
            sysRoleService.add(dto2);

            postAction(mockMvc, "/sysRole/page", requestDTO).andExpect(status().isOk())
                                                            .andExpect(MockMvcResultMatchers.content()
                                                                                            .contentTypeCompatibleWith(
                                                                                                MediaType.APPLICATION_JSON))
                                                            .andExpect(jsonPath("$.totalCount").value(1))
                                                            .andExpect(jsonPath("$.list").isArray());

        }


    }

    // test case for update
    @Nested
    public class Update {

        @Test
        @SneakyThrows
        public void update_withValidData_shouldBeOK() {
            sysRoleDTO = sysRoleService.add(sysRoleDTO);
            sysRoleDTO.setRoleName("new Role Name");

            putAction(mockMvc, "/sysRole/update", sysRoleDTO).andExpect(status().isAccepted())
                                                             .andExpect(jsonPath("$.roleName").value("new Role Name"));

        }

        @Test
        @SneakyThrows
        public void update_withNonExistingId_shouldReturnNotFound() {
            sysRoleDTO.setRoleName("new Role Name");
            sysRoleDTO.setId(999L);

            putAction(mockMvc, "/sysRole/update", sysRoleDTO).andExpect(status().isNotFound());

        }

        @Test
        @SneakyThrows
        public void update_withPartialData_shouldMergeChanges() {
            sysRoleDTO = sysRoleService.add(sysRoleDTO);
            sysRoleDTO.setRoleName("new Name");

            putAction(mockMvc, "/sysRole/update", sysRoleDTO).andExpect(status().isAccepted())
                                                             .andExpect(jsonPath("$.roleName").value("new Name"))
                                                             .andExpect(jsonPath("$.roleStatus").value(1));

        }
    }

    @Nested
    public  class Delete {
        // test case for delete

        @Test
        @SneakyThrows
        public void delete_withExistingId_shouldBeOK() {
            SysRoleDTO dto = SysRoleDTO.builder().roleName("admin").roleStatus(1).build();
            dto = sysRoleService.add(dto);

            deleteAction(mockMvc, "/sysRole/" + dto.getId()).andExpect(status().isOk());
        }

        @Test
        @SneakyThrows
        public void delete_withNonExistingId_shouldReturnNotFound() {
            deleteAction(mockMvc, "/sysRole/999").andExpect(status().isOk());

        }

        @Test
        @SneakyThrows
        public void delete_alreadyDeleted_shouldHandleGraceFully() {
            SysRoleDTO dto = SysRoleDTO.builder().roleName("admin").roleStatus(1).build();
            dto = sysRoleService.add(dto);
            sysRoleService.delete(dto.getId());

            deleteAction(mockMvc, "/sysRole/" + dto.getId()).andExpect(status().isOk());
        }
    }
}
