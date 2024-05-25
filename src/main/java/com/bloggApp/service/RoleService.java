package com.bloggApp.service;

import com.bloggApp.dto.RoleDTO;
import com.bloggApp.payload.ApiResponse;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(Long roleId, RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleyId(Long roleId);

    ApiResponse deleteRole(Long roleId);
}
