package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroRequest(
        @NotBlank(message = "Nome é obrigatório")
       String nome,

        @NotBlank @Email(message = "Email inválido")
       String email,

        @NotBlank(message = "Senha é obrigatória")
       String senha,

        @NotNull(message = "Role é obrigatória")
       Usuario.Role role

){}

