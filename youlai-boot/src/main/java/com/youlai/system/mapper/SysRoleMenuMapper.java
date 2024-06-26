package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.model.bo.RolePermsBO;
import com.youlai.system.model.entity.SysRoleMenu;
import com.youlai.system.model.form.RoleForm;
import com.youlai.system.model.vo.RolePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单访问层
 *
 * @author haoxr
 * @since 2022/6/4
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     */
    List<RolePermsBO> getRolePermsList(String roleCode);

    int insertRole(RoleForm roleForm);

    int deleteOneRoles(String ids);

    int updateRole(Long id,RoleForm roleForm);


    Page<RolePageVO> queryByKeywords(IPage<RolePageVO> page,@Param("keywords") String keywords);
}
