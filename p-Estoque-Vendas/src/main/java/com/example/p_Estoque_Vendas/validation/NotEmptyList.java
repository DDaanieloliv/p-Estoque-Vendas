package com.example.p_Estoque_Vendas.validation;

import com.example.p_Estoque_Vendas.validation.constraintValidation.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}

// Esta é uma anotação de validação personalizada em Java, criada para ser utilizada no
// contexto de validação de objetos em uma aplicação Spring.

// [@Retention(RetentionPolicy.RUNTIME):]
//
//  Indica que a anotação NotEmptyList será retida durante o tempo de execução, o que
//  significa que será acessível através de reflexão durante a execução do programa.
//
//
// [@Target(ElementType.FIELD):]
//
//  Define que a anotação NotEmptyList pode ser aplicada apenas a elementos do tipo campo
//  (FIELD), o que significa que ela é destinada a ser usada para validar campos de classe.
//
//
// [@Constraint(validatedBy = NotEmptyListValidator.class):]
//
//  Indica que a implementação real da lógica de validação desta anotação é fornecida pela
//  classe NotEmptyListValidator. Isso permite que você defina logicamente como a validação
//  da lista não vazia deve ser realizada.
//
//
// [public @interface NotEmptyList:]
//
//  *   Declaração da própria anotação personalizada. Aqui estão alguns elementos
//      importantes dentro dessa declaração:
//
//      - String message() default "A lista não pode ser vazia.";:
//
//          Especifica a mensagem padrão que será usada quando a validação falhar.
//
//      - Class<?>[] groups() default { };:
//
//          Permite especificar grupos de validação. Esses grupos podem ser usados para
//          aplicar diferentes conjuntos de validações em diferentes situações.
//
//      - Class<? extends Payload>[] payload() default { };:
//
//          Permite associar informações arbitrarias ao contrato da anotação. Em muitos
//          casos, isso não é utilizado e o array padrão vazio é mantido.
//
//
// Essa anotação @NotEmptyList é projetada para ser colocada em campos de classe (atributos)
// e, quando usada, a lógica de validação definida em NotEmptyListValidator será acionada
// para garantir que a lista associada ao campo não esteja vazia. Isso é útil para garantir
// que coleções em seus objetos não estejam nulas ou vazias quando a validação é aplicada.
