package com.bloggApp.service.impl;

import com.bloggApp.dto.RoleDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Role;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.repository.RoleRepository;
import com.bloggApp.service.RoleService;
import com.bloggApp.utils.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMappers modelMappers;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = this.modelMappers.roleDtoToRoleEntity(roleDTO);
        Role savedRole = this.roleRepository.save(role);
        return this.modelMappers.RoleEntityToRoleDto(savedRole);
    }

    @Override
    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role is not found with this id :"+roleId));
        role.setName(role.getName());
        Role updatedRole = this.roleRepository.save(role);
        return this.modelMappers.RoleEntityToRoleDto(updatedRole);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(role -> modelMappers.RoleEntityToRoleDto(role)).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleyId(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role not found with this id :"+roleId));
        return this.modelMappers.RoleEntityToRoleDto(role);
    }

    @Override
    public ApiResponse deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role not found with this id :"+roleId));
        roleRepository.delete(role);
        return new ApiResponse("User deleted successfully with id " + roleId, true);
    }
}
