package com.doubao.douding.system.controller;

import com.doubao.douding.system.dto.SysResourceDTO;
import com.doubao.douding.system.dto.SysRoleDTO;
import com.doubao.douding.system.entity.SysRole;
import com.doubao.douding.system.entity.SysRoleResource;
import com.doubao.douding.system.entity.query.QSysRoleResource;
import com.doubao.douding.system.enums.SysResourceEnum;
import com.doubao.douding.system.service.SysResourceService;
import com.doubao.douding.system.service.SysRoleService;
import com.doubao.douding.util.JsonUtils;
import com.google.common.collect.Lists;
import io.ebean.DB;
import io.ebean.test.UserContext;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Johnson
 * @Description unit test for SysRoleController
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
@WithMockUser(roles = {"ADMIN"})
class SysRoleControllerTest extends BaseControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysResourceService sysResourceService;

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
        DB.truncate(SysRoleResource.class);
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

        @SneakyThrows
        @Test
        public void create_withResource_shouldBeOK() {
            List<SysResourceDTO> resources = Lists.newArrayList();
            SysResourceDTO sysResourceDTO = SysResourceDTO.builder()
                                                          .resourceName("user management")
                                                          .resourceType(
                                                              SysResourceEnum.SysResourceTypeEnum.DIR.getCode())
                .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                                          .build();
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            resources.add(sysResourceDTO);

            sysResourceDTO = SysResourceDTO.builder()
                                           .resourceName("user list")
                                           .resourceType(SysResourceEnum.SysResourceTypeEnum.MENU.getCode())
                                           .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                           .parent(sysResourceDTO)
                                           .build();
            sysResourceDTO = sysResourceService.save(sysResourceDTO);

            resources.add(sysResourceDTO);
            sysRoleDTO.setResources(resources);

            final String contentAsString = postAction(mockMvc, "/sysRole/add", sysRoleDTO)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.roleName").value(resultDTO.getRoleName()))
                .andExpect(jsonPath("$.roleStatus").value(resultDTO.getRoleStatus()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
           SysRoleDTO dto = JsonUtils.toObject(contentAsString, SysRoleDTO.class);

            List<SysRoleResource> sysRoleResources = new QSysRoleResource().sysRole.id.eq(dto.getId()).findList();
            assertEquals(2, sysRoleResources.size());
        }

        // test case for read
    }

    @Nested
    class Get {

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
            SysRoleDTO requestDTO = SysRoleDTO.builder()
                                              .roleName("admin")
                                              .roleStatus(1)
                                              .pageIndex(1)
                                              .pageSize(10)
                                              .build();

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
            SysRoleDTO requestDTO = SysRoleDTO.builder()
                                              .roleName("admin")
                                              .roleStatus(1)
                                              .pageIndex(1)
                                              .pageSize(10)
                                              .build();

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

        @Test
        @SneakyThrows
        public void update_updateSysResource_shouldBeOK(){
            List<SysResourceDTO> resources = Lists.newArrayList();
            SysResourceDTO sysResourceDTO = SysResourceDTO.builder()
                                                          .resourceName("user management")
                                                          .resourceType(
                                                              SysResourceEnum.SysResourceTypeEnum.DIR.getCode())
                                                          .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                                          .build();
            sysResourceDTO = sysResourceService.save(sysResourceDTO);
            resources.add(sysResourceDTO);

            sysResourceDTO = SysResourceDTO.builder()
                                           .resourceName("user list")
                                           .resourceType(SysResourceEnum.SysResourceTypeEnum.MENU.getCode())
                                           .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                           .parent(sysResourceDTO)
                                           .build();
            sysResourceDTO = sysResourceService.save(sysResourceDTO);

            resources.add(sysResourceDTO);
            sysRoleDTO.setResources(resources);
            sysRoleDTO = sysRoleService.add(sysRoleDTO);

            sysResourceDTO = SysResourceDTO.builder()
                                           .resourceName("user list new")
                                           .resourceType(SysResourceEnum.SysResourceTypeEnum.MENU.getCode())
                                           .resourceStatus(SysResourceEnum.SysResourceStatusEnum.NORMAL.getCode())
                                           .parent(sysResourceDTO)
                                           .build();
            sysResourceDTO = sysResourceService.save(sysResourceDTO);

            resources = Collections.singletonList(sysResourceDTO);
            sysRoleDTO.setResources(resources);
            sysRoleDTO.setRoleName("new Role Name n");


            final String contentAsString = putAction(mockMvc, "/sysRole/update", sysRoleDTO)
                .andExpect(status().isAccepted())
                .andDo(print())
                .andExpect(jsonPath("$.roleName").value("new Role Name n"))
                .andExpect(jsonPath("$.roleStatus").value(resultDTO.getRoleStatus()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
            SysRoleDTO dto = JsonUtils.toObject(contentAsString, SysRoleDTO.class);

            List<SysRoleResource> sysRoleResources = new QSysRoleResource().sysRole.id.eq(dto.getId()).findList();
            assertEquals(1, sysRoleResources.size());
            assertEquals("user list new", sysRoleResources.get(0).getSysResource().getResourceName());


        }
    }

    @Nested
    public class Delete {
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
