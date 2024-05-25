package com.bloggApp.controller;

import com.bloggApp.dto.RoleDTO;
import com.bloggApp.modal.Role;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(RoleDTO roleDTO){
        RoleDTO newRole = roleService.createRole(roleDTO);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long roleId, RoleDTO roleDTO){
        RoleDTO updateRole = roleService.updateRole(roleId, roleDTO);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAll(){
        List<RoleDTO> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Long roleId){
        RoleDTO roleDTO = roleService.getRoleyId(roleId);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Long roleId){
       return ResponseEntity.ok(roleService.deleteRole(roleId));
    }
}
