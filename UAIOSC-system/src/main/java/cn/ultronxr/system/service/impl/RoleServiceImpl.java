package cn.ultronxr.system.service.impl;

import cn.ultronxr.system.bean.mybatis.bean.Role;
import cn.ultronxr.system.bean.mybatis.mapper.RoleMapper;
import cn.ultronxr.system.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/13 14:03
 * @description
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> queryRole(Role role) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        if(role != null) {
            wrapper.like(StringUtils.isNotEmpty(role.getName()), Role::getName, role.getName())
                    .like(StringUtils.isNotEmpty(role.getCode()), Role::getCode, role.getCode())
                    .like(StringUtils.isNotEmpty(role.getNote()), Role::getNote, role.getNote());
        }
        return roleMapper.selectList(wrapper);
    }
}
