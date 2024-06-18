package com.example.p_Estoque_Vendas.validation.constraintValidation;

import com.example.p_Estoque_Vendas.validation.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }
}

// [@Override public void initialize(NotEmptyList constraintAnnotation)
// { ConstraintValidator.super.initialize(constraintAnnotation); }:]
//
//  Este método é chamado durante a inicialização do validador. Ele é utilizado para
//  realizar qualquer configuração adicional com base nas informações contidas na
//  anotação NotEmptyList. Neste caso, o método simplesmente chama a implementação padrão,
//  indicada por ConstraintValidator.super.initialize(constraintAnnotation);, o que
//  significa que não há nenhuma configuração adicional específica sendo feita na
//  inicialização.
//
//
// [@Override public boolean isValid
// (List list, ConstraintValidatorContext constraintValidatorContext)
// { return list != null && !list.isEmpty(); }:]
//
// Este método é o núcleo da lógica de validação. Ele verifica se a lista fornecida
// (List list) não é nula e não está vazia. Se a lista atender a essas condições, o método
// retorna true, indicando que a validação foi bem-sucedida. Caso contrário, se a lista for
// nula ou vazia, o método retorna false, indicando que a validação falhou.
//
// A anotação @Override é usada para indicar que esses métodos estão substituindo métodos
// da interface ConstraintValidator<NotEmptyList, List>. Essa interface é parte do sistema
// de validação do Jakarta Bean Validation e define métodos que um validador personalizado
// deve implementar.
//
// Resumidamente, esse código implementa um validador personalizado chamado
// NotEmptyListValidator, que é associado à anotação @NotEmptyList. Esse validador verifica
// se uma lista é não nula e não vazia.
