package cn.ultronxr.valorant.service.impl;

import cn.ultronxr.valorant.bean.mybatis.bean.Weapon;
import cn.ultronxr.valorant.bean.mybatis.mapper.WeaponMapper;
import cn.ultronxr.valorant.service.WeaponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/02/21 11:27
 * @description
 */
@Service
@Slf4j
public class WeaponServiceImpl extends ServiceImpl<WeaponMapper, Weapon> implements WeaponService {
}
