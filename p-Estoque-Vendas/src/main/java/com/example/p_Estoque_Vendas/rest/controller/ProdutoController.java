package com.example.p_Estoque_Vendas.rest.controller;

/*
A escolha de usar ou não Data Transfer Objects (DTOs) diretamente no controller de uma
aplicação está relacionada às necessidades específicas do projeto, estilo arquitetônico
adotado, complexidade do domínio e outros fatores. Aqui estão algumas razões pelas quais
algumas aplicações optam por não usar DTOs no controller e, em vez disso, usam as entidades
diretamente:

Simplicidade e Agilidade:

Em aplicações mais simples, especialmente aquelas onde a estrutura do modelo de dados é
semelhante às necessidades da interface do usuário, pode ser mais ágil e direto usar as
entidades diretamente no controller. Isso reduz a necessidade de criar classes adicionais
(DTOs) e mapeamentos entre elas.


Operações Básicas:

Para operações CRUD (Create, Read, Update, Delete) simples, onde os dados da entidade podem
ser diretamente refletidos nas operações da interface do usuário, o uso direto das entidades
pode ser suficiente.


Menos Camadas, Menos Complexidade:

Em arquiteturas mais simples, com menos camadas (por exemplo, MVC tradicional), a
introdução de DTOs pode parecer excessiva e adicionar complexidade desnecessária.


Pequeno Escopo e Equipe Pequena:

Em projetos de pequeno escopo, especialmente aqueles desenvolvidos por equipes pequenas, a
sobrecarga de criar e manter DTOs pode superar os benefícios.


Modelagem Rica no Frontend:

Se o frontend espera receber dados em um formato que se alinha diretamente com a estrutura
da entidade, pode ser mais eficiente transferir as entidades diretamente.


--Entretanto, é importante considerar algumas
desvantagens do uso direto de entidades no controller:--

Overposting de Dados:

Pode resultar no envio de mais dados do que o necessário para o frontend, incluindo
informações sensíveis ou desnecessárias.


Acoplamento Rígido:

A mudança na estrutura interna da entidade pode impactar diretamente a interface do
usuário, resultando em um acoplamento mais rígido.


Exposição de Detalhes Internos:

Detalhes internos da entidade, que podem não ser relevantes para a interface do usuário,
podem ser expostos inadvertidamente.


Em muitos projetos, especialmente aqueles de médio a grande porte e/ou seguindo princípios
de arquitetura mais avançados, o uso de DTOs é preferido para proporcionar uma separação
mais clara entre as camadas da aplicação, reduzir o acoplamento e fornecer flexibilidade
para ajustes futuros. A escolha entre usar ou não DTOs no controller depende da arquitetura,
requisitos e preferências da equipe de desenvolvimento.
*/


import com.example.p_Estoque_Vendas.domain.entity.Produto;
import com.example.p_Estoque_Vendas.domain.repository.Produtos;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
//import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {


    private final Produtos repository;

    private final UserRepository userRepository;

    public ProdutoController(Produtos repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Operation(
            description = "Get product by ID",
            summary = "This is a summary for product get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                    "{\n" +
                                            "  \"id\": 0,\n" +
                                            "  \"preco\": 0,\n" +
                                            "  \"user\": {\n" +
                                            "    \"userId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                                            "    \"username\": \"string\",\n" +
                                            "    \"password\": \"string\",\n" +
                                            "    \"roles\": [\n" +
                                            "      {\n" +
                                            "        \"roleId\": 0,\n" +
                                            "        \"name\": \"string\"\n" +
                                            "      }\n" +
                                            "    ]\n" +
                                            "  },\n" +
                                            "  \"creationTimeStamp\": \"2024-04-03T00:00:00.000Z\",\n" +
                                            "  \"descricao\": \"string\"\n" +
                                            "}"
                            ))
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @GetMapping("{id_product}")
    public Produto getProductById(@PathVariable Integer id_product) {
        return repository
                .findById(id_product)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    //Caso queira queira optarpor não ter que digitar 'HTTPStatus.' //
    //adicione esse import 'import static org.springframework.http.HttpStatus.*;' //
    @Operation(
            summary = "Save a new product",
            description = "Creates a new product and associates it with the user identified by the JWT token.",

            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully",
                            content = @Content(
                                    mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                            "{\n" +
                                                    "  \"id\": 0,\n" +
                                                    "  \"preco\": 0,\n" +
                                                    "  \"user\": {\n" +
                                                    "    \"userId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                                                    "    \"username\": \"string\",\n" +
                                                    "    \"password\": \"string\",\n" +
                                                    "    \"roles\": [\n" +
                                                    "      {\n" +
                                                    "        \"roleId\": 0,\n" +
                                                    "        \"name\": \"string\"\n" +
                                                    "      }\n" +
                                                    "    ]\n" +
                                                    "  },\n" +
                                                    "  \"creationTimeStamp\": \"2024-04-03T00:00:00.000Z\",\n" +
                                                    "  \"descricao\": \"string\"\n" +
                                                    "}"
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduct(@RequestBody @Valid Produto produto, JwtAuthenticationToken token ) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        if (user.isPresent()) {
            produto.setUser(user.get());
            return repository.save(produto);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
    }
}

    @Operation(
            summary = "Delete product by ID",
            description = "Deletes the product identified by the given ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    @DeleteMapping("{id_product}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer id_product) {
        repository.findById(id_product)
                .map(produto -> {
                    repository.delete(produto);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @Operation(
            summary = "Update product by ID",
            description = "Updates the product identified by the given ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Product updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    @PutMapping("{id_product}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable Integer id_product,
                              @RequestBody @Valid Produto produto) {
        //@Valid tambem é necessario aqui para que as atulizaões feitas sejam validas//
        repository.findById(id_product)
                .map(produtoExistente -> {

                    produto.setId(produtoExistente.getId());
                    // Preserve o user do produtoExistente
                    produto.setUser(produtoExistente.getUser());
                    produto.setCreationTimeStamp(produtoExistente.getCreationTimeStamp());
                    // Atualize os outros campos do produto
                    produto.setPreco(produto.getPreco());
                    produto.setDescricao(produto.getDescricao());
                    repository.save(produto);

//                    produto.setUser(produtoExistente.getUser());
//                    produto.setCreationTimeStamp(produtoExistente.getCreationTimeStamp());
//                    produto.setId(produtoExistente.getId());
//                    //Substitue o produto do BD pelo passado no '@RequestBody'//
//
//                    repository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @Operation(
            summary = "Find products with filter",
            description = "Finds products based on filter criteria.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Products found",
                            content = @Content(
                                    mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                    "[\n" +
                                            "  {\n" +
                                            "    \"id\": 0,\n" +
                                            "    \"preco\": 0,\n" +
                                            "    \"user\": {\n" +
                                            "      \"userId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                                            "      \"username\": \"string\",\n" +
                                            "      \"password\": \"string\",\n" +
                                            "      \"roles\": [\n" +
                                            "        {\n" +
                                            "          \"roleId\": 0,\n" +
                                            "          \"name\": \"string\"\n" +
                                            "        }\n" +
                                            "      ]\n" +
                                            "    },\n" +
                                            "    \"creationTimeStamp\": \"2024-04-03T00:00:00.000Z\",\n" +
                                            "    \"descricao\": \"string\"\n" +
                                            "  }\n" +
                                            "]"
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid filter criteria"
                    )
            }
    )
    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);


    }




}

