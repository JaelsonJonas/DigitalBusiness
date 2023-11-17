package br.com.iriscareapi.dto;

import br.com.iriscareapi.entities.Child;

public record ChildFindDTO(String name,
                           String cpf,
                           String birthday,
                           Boolean active) {}
