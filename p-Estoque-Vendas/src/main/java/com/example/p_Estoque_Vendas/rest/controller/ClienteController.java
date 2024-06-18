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


import com.example.p_Estoque_Vendas.domain.entity.Cliente;
import com.example.p_Estoque_Vendas.domain.repository.Clientes;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

//@Controller
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private Clientes clientes;

    private final UserRepository userRepository;

    public ClienteController(Clientes clientes, UserRepository userRepository) {
        this.clientes = clientes;
        this.userRepository = userRepository;
    }


    @Operation(
            summary = "Get Cliente by ID",
            description = "Fetches a Cliente entity by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Example 200", value =
                                            "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"user\": {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"username\": \"user_example\"\n" +
                                                    "  },\n" +
                                                    "  \"creationTimeStamp\": \"2024-06-04T10:00:00Z\",\n" +
                                                    "  \"cpf\": \"12345678901\",\n" +
                                                    "  \"nome\": \"Nome Exemplo\",\n" +
                                                    "  \"pedidos\": []\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @GetMapping("{id_client}" )
    @ResponseBody
    public Cliente getClienteById( @PathVariable Integer id_client ) {
        return clientes
                .findById(id_client)
                .orElseThrow( () ->
                        new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Cliente não encontrado"));
//'or else throw' => 'se não lance'//

//        if ( cliente.isPresent() ){
//            return cliente.get();
//            ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(HttpStatus.OK);
//            return  ResponseEntity.ok(cliente.get());
//        }


    }


    @Operation(
            summary = "Save a new Cliente",
            description = "Creates a new Cliente and associates it with the user identified by the JWT token.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(name = "Example 201", value =
                                            "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"user\": {\n" +
                                                    "    \"id\": 2,\n" +
                                                    "    \"username\": \"user_example\"\n" +
                                                    "  },\n" +
                                                    "  \"creationTimeStamp\": \"2024-06-04T10:00:00Z\",\n" +
                                                    "  \"cpf\": \"12345678901\",\n" +
                                                    "  \"nome\": \"Nome Exemplo\",\n" +
                                                    "  \"pedidos\": []\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request - CPF already exists",
                            content = @Content()
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
//    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente , JwtAuthenticationToken token) {
        // Verificar se já existe um cliente com o mesmo CPF
        if (clientes.findByCpf(cliente.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um cliente com este CPF.");
        }

        // Continuar com a operação de salvar o cliente
        var user = userRepository.findById(UUID.fromString(token.getName()));

        cliente.setUser(user.get());

        return  clientes.save(cliente);
    }



    @Operation(
            summary = "Delete a Cliente",
            description = "Deletes a Cliente identified by the provided ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente deleted successfully",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @DeleteMapping("{id_client}")
//    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id_client ) {
        clientes.findById(id_client)
                .map( cliente -> {clientes.delete(cliente);
                    return cliente; })
                .orElseThrow( () -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cliente não encontrado"));


//        Optional<Cliente> cliente = clientes.findById(id_client);
//
//        if (cliente.isPresent()) {
//            clientes.delete( cliente.get() );
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.notFound().build();
    }


    @Operation(
            summary = "Update a Cliente",
            description = "Updates a Cliente identified by the provided ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente updated successfully",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente not found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid data",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @PutMapping("{id_client}")
//    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@PathVariable Integer id_client,
                        @RequestBody Cliente cliente ) {
        clientes
                .findById(id_client)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    //Substitue o cliente do BD pelo passado no '@RequestBody'//
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow( () -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }



    @Operation(
            summary = "Find Clientes",
            description = "Retrieves a list of Clientes based on the provided filter criteria. All string fields are matched using a case-insensitive 'contains' matcher.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Cliente.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid filter criteria",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content()
                    )
            }
    )
    @GetMapping
    public List<Cliente> find( Cliente filtro ) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );

        Example example =  Example.of(filtro, matcher);
        return clientes.findAll(example);


//        String sql = "select * from cliente ";
//
//        if (filtro.getNome() != null) {
//            sql += " where nome = :nome ";
//        }
//
//        if (filtro.getCpf() != null) {
//            sql += "and cpf= ";
//        }
    }


}


// @Controller e @RestController:
//
//  * @Controller:
//      Essa anotação é usada em classes para indicar que elas são controladores no contexto
//      do Spring MVC. Controladores geralmente são responsáveis por gerenciar solicitações HTTP.
//
//  * @RestController:
//      É uma versão especializada do @Controller. A principal diferença é que, enquanto
//      @Controller retorna principalmente visões (por exemplo, páginas HTML), @RestController
//      retorna principalmente dados no formato JSON ou XML. É uma combinação de @Controller
//      e @ResponseBody.
//
//
// @RequestMapping("/api/clientes"):
//
//  * @RequestMapping:
//      Essa anotação é usada para mapear solicitações HTTP para métodos específicos ou para
//      todoo controlador.
//
//  * ("/api/clientes"):
//      Define o caminho base para todas as solicitações manipuladas por esse controlador. No
//      caso, todas as solicitações começando com "/api/clientes" serão tratadas por este
//      controlador.



// "private Clientes clientes;"
//
//  * private Clientes clientes;:
//      Declaração de um campo (atributo) privado chamado clientes. O tipo desse campo é
//      Clientes (repository JPA), o que sugere que seja um componente que gerencia operações
//      relacionadas a clientes.
//
//
// --public ClienteController(Clientes clientes) { this.clientes = clientes; }--
//
//  * public ClienteController(Clientes clientes) {:
//      Declaração do construtor da classe ClienteController que aceita um parâmetro do tipo
//      Clientes. Isso significa que, ao criar uma instância de ClienteController, é
//      necessário fornecer uma implementação de Clientes.
//
//  * this.clientes = clientes;:
//      Atribuição do parâmetro clientes ao campo da classe ClienteController. Isso é
//      conhecido como injeção de dependência, onde a implementação específica de Clientes é
//      passada para a classe ClienteController por meio do construtor.



// --@GetMapping("{id_client}" )--
//
//  * @GetMapping("{id_client}" ):
//      Anotação que mapeia a solicitação HTTP GET para a URL "/api/clientes/{id_client}", onde
//      {id_client} é uma variável de caminho. Isso significa que a função getClienteById será
//      chamada quando houver uma solicitação GET para um caminho que corresponda a esse padrão.
//
//
// --public Cliente getClienteById( @PathVariable Integer id_client ) {--
//
//  * public Cliente getClienteById( @PathVariable Integer id_client ) {:
//      Declaração do método getClienteById, que manipula solicitações GET para obter
//      informações sobre um cliente com base no ID fornecido como parte do caminho.
//
//  * @PathVariable Integer id_client:
//      Anotação que indica que o parâmetro id_client é extraído do caminho da URL. Ele
//      representa o ID do cliente a ser recuperado.
//
//
// --return clientes.findById(id_client).orElseThrow( () ->
// new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));--
//
//  * return clientes.findById(id_client):
//      Chama o método findById no objeto clientes, que representa o repositório de clientes.
//      Esse método procura um cliente no banco de dados com o ID fornecido.
//
//  * '.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//  "Cliente não encontrado"))':
//      Utiliza o método orElseThrow para retornar o cliente encontrado ou lançar uma exceção
//      do tipo ResponseStatusException com status 404 (Not Found) e uma mensagem indicando
//      que o cliente não foi encontrado.



// --@PostMapping--
//
//  * @PostMapping:
//      Anotação que mapeia a solicitação HTTP POST para a URL "/api/clientes". Isso significa
//      que a função save será chamada quando houver uma solicitação POST para esse caminho.
//
//
// --@ResponseBody--
//
//  * // @ResponseBody:
//      Este é um comentário que geralmente seria usado para indicar que o valor retornado
//      pelo método deve ser considerado como o corpo da resposta HTTP. No entanto, no contexto
//      do Spring Framework, a anotação @RestController já implica que todos os métodos, por
//      padrão, retornarão o corpo da resposta.
//
//
// --@ResponseStatus(HttpStatus.CREATED)--
//
//  * @ResponseStatus(HttpStatus.CREATED):
//      Anotação que define o código de status HTTP 201 (Created) para a resposta. Isso indica
//      que a criação do recurso (novo cliente) foi bem-sucedida.
//
//
// --public Cliente save( @RequestBody Cliente cliente )--
//
//  * public Cliente save( @RequestBody Cliente cliente ):
//      Declaração do método save, que manipula solicitações POST para criar um novo cliente.
//
//  * @RequestBody Cliente cliente:
//      Anotação que indica que o objeto cliente é obtido do corpo da solicitação. Ele
//      representa os dados do cliente a serem criados no sistema.
//
//
// --return clientes.save(cliente);--
//
//  * return clientes.save(cliente);:
//      Chama o método save no objeto clientes, que representa o repositório de clientes. Esse
//      método salva o novo cliente no banco de dados e retorna o cliente recém-criado.




// --@DeleteMapping("{id_client}")--
//
//  * @DeleteMapping("{id_client}"):
//      Anotação que mapeia a solicitação HTTP DELETE para a URL "/api/clientes/{id_client}".
//      Isso significa que a função delete será chamada quando houver uma solicitação DELETE
//      para esse caminho, e {id_client} é uma variável de caminho que representa o ID do
//      cliente a ser excluído.
//
//
// --@ResponseStatus(HttpStatus.NO_CONTENT)--
//
//  * @ResponseStatus(HttpStatus.NO_CONTENT):
//      Anotação que define o código de status HTTP 204 (No Content) para a resposta. Isso
//      indica que a solicitação foi processada com sucesso, mas não há conteúdo a ser
//      retornado.
//
//
// --public void delete( @PathVariable Integer id_client )--
//
//  * public void delete( @PathVariable Integer id_client ):
//      Declaração do método delete, que manipula solicitações DELETE para excluir um cliente
//      existente.
//
//  * @PathVariable Integer id_client:
//      Anotação que indica que a variável id_client é obtida do caminho da solicitação. Ela
//      representa o ID do cliente a ser excluído.
//
//
// --clientes.findById(id_client).map(...) .orElseThrow(...)--
//
//  * clientes.findById(id_client):
//      Usa o método findById do repositório clientes para procurar um cliente no banco de
//      dados com o ID fornecido.
//
//  * .map( cliente -> {clientes.delete(cliente); return cliente; }):
//      Se o cliente for encontrado, executa um bloco de código que exclui o cliente usando
//      clientes.delete(cliente) e retorna o cliente excluído.
//
//      ## 'clientes.findById(id_client)':
//          Este trecho retorna um Optional que pode conter um objeto Cliente se ele existir
//          no repositório, ou pode estar vazio se o cliente não for encontrado.
//
//      ## '.map(cliente -> {clientes.delete(cliente); return cliente; })':
//          O método map é uma operação que pode ser aplicada a um objeto Optional. Ele permite
//          realizar uma operação no valor contido no Optional, caso exista. Neste caso, a
//          operação é definida por uma expressão lambda que recebe o cliente do Optional (se
//          estiver presente) como parâmetro.
//
//      ## '(->)':
//          É usada para representar uma expressão lambda em Java. A expressão lambda é uma
//          característica introduzida no Java 8 que permite a criação de funções anônimas de
//          forma mais concisa. No contexto de coleções, como o método map que você está vendo,
//          a expressão lambda é usada para definir uma operação que será aplicada a cada
//          elemento da coleção.
//
//      ## 'cliente ->':
//          Isso define um parâmetro chamado cliente que será usado na expressão lambda.
//
//      ## '{clientes.delete(cliente); return cliente; }':
//          Isso representa o corpo da expressão lambda. Neste caso, está realizando duas
//          operações:
//
//      ## 'clientes.delete(cliente)':
//          Exclui o cliente do repositório.
//
//      ## 'return cliente;':
//          Retorna o cliente excluído..
//
//      Se o cliente estiver presente (ou seja, se for encontrado), a expressão lambda dentro
//      do .map será executada. Nessa expressão lambda, o cliente é excluído do repositório
//      (clientes.delete(cliente)) e, em seguida, o mesmo cliente é retornado (return cliente).
//      O resultado final será um Optional que agora pode conter o cliente excluído.
//
//  * .orElseThrow(() ->
//    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")):
//      Se o cliente não for encontrado, lança uma exceção ResponseStatusException com código
//      de status 404 (Not Found) e uma mensagem indicando que o cliente não foi encontrado.




// --@PutMapping("{id_client}")--
//
//  * @PutMapping("{id_client}"):
//      Anotação que mapeia a solicitação HTTP PUT para a URL "/api/clientes/{id_client}".
//      Isso significa que a função update será chamada quando houver uma solicitação PUT para
//      esse caminho, e {id_client} é uma variável de caminho que representa o ID do cliente
//      a ser atualizado.
//
//
//
// --@ResponseStatus(HttpStatus.NO_CONTENT)--
//
//  * @ResponseStatus(HttpStatus.NO_CONTENT):
//      Anotação que define o código de status HTTP 204 (No Content) para a resposta. Isso
//      indica que a solicitação foi processada com sucesso, mas não há conteúdo a ser
//      retornado.
//
//
//
// --public void update(@PathVariable Integer id_client, @RequestBody Cliente cliente)--
//
//  * public void update(@PathVariable Integer id_client, @RequestBody Cliente cliente):
//      Declaração do método update, que manipula solicitações PUT para atualizar um cliente
//      existente.
//
//  * @PathVariable Integer id_client:
//      Anotação que indica que a variável id_client é obtida do caminho da solicitação. Ela
//      representa o ID do cliente a ser atualizado.
//
//  *@RequestBody Cliente cliente: Anotação que indica que o objeto cliente é obtido do corpo
//  da solicitação. Este é o cliente com os dados atualizados que será utilizado na
//  atualização.
//
//
//
// --clientes.findById(id_client).map(...) .orElseThrow(...)--
//
//  * clientes.findById(id_client):
//      Usa o método findById do repositório clientes para verificar se o cliente com o ID
//      fornecido existe.
//
//  * .map( clienteExistente -> {...}).orElseThrow(() ->
//  new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")):
//      Se o cliente for encontrado, executa um bloco de código que atualiza os dados do
//      cliente existente com os dados do cliente recebido e salva no banco de dados usando
//      clientes.save(cliente). Retorna o cliente existente.
//
//
//      --clienteExistente -> {--
//
//      Isso parece ser uma expressão lambda ou uma função anônima. Neste caso,
//      clienteExistente é o parâmetro da função que representa um cliente já existente no
//      banco de dados.
//
//      --cliente.setId(clienteExistente.getId());--
//
//      Aqui, o código está copiando o ID do clienteExistente para o cliente que está
//      sendo modificado. Isso é comum ao atualizar uma entidade existente para garantir
//      que o ID permaneça o mesmo.
//
//      --clientes.save(cliente);--
//
//      Parece que clientes é um repositório JPA ou um serviço que lida com operações
//      relacionadas a clientes. O método save geralmente é usado para salvar
//      (ou atualizar) uma entidade no banco de dados. Neste caso, o cliente está sendo
//      salvo.
//
//      --return clienteExistente;--
//
//      A função retorna o clienteExistente. Isso pode ser útil se, por algum motivo, você
//      precisar ter uma referência ao cliente existente após a atualização.
//
//      No geral, esse trecho de código sugere uma lógica de atualização do cliente. Ele
//      verifica se o cliente já existe (representado pelo clienteExistente), copia o ID
//      do cliente existente para o novo cliente, salva o novo cliente (efetivamente
//      atualizando-o no banco de dados) e, finalmente, retorna uma referência ao cliente
//      existente. Isso pode ser útil em situações em que você precisa fornecer feedback
//      sobre a atualização ou usar o cliente atualizado em algum contexto específico.
//
//
//  * Se o cliente não for encontrado, lança uma exceção ResponseStatusException com código
//  de status 404 (Not Found) e uma mensagem indicando que o cliente não foi encontrado.



// --@GetMapping--
//
//  * @GetMapping:
//  Anotação que mapeia solicitações HTTP GET para o método find. Neste caso, representa a
//  URL "/api/clientes".
//
// --public List<Cliente> find(Cliente filtro)--
//
//  * public List<Cliente> find(Cliente filtro):
//      Declaração do método find que lida com solicitações GET para buscar clientes com base
//      em um filtro.
//
//  * Cliente filtro:
//      Parâmetro que representa o filtro de busca, contendo os critérios pelos quais os
//      clientes serão consultados.
//
//
//
// --ExampleMatcher matcher = ExampleMatcher...--
//
//  * ExampleMatcher matcher = ExampleMatcher...:
//      Cria um objeto ExampleMatcher, que configura como a correspondência será realizada
//      para a consulta. Neste caso, está configurado para corresponder parcialmente
//      (contendo) e sem diferenciar maiúsculas e minúsculas.
//
//
//
// --Example example = Example.of(filtro, matcher)--
//
//  * Example example = Example.of(filtro, matcher):
//      Cria um objeto Example usando o filtro e o ExampleMatcher. Isso será usado para
//      realizar a consulta.
//
//
//
// --return clientes.findAll(example)--
//
//  * return clientes.findAll(example):
//      Usa o método findAll do repositório clientes para buscar todos os clientes que
//      correspondem ao exemplo fornecido. O resultado é uma lista de clientes que atendem aos
//      critérios do filtro.
//
//
//
// --Comentários relacionados a SQL (linhas comentadas)--
//
//  Os comentários indicam que, originalmente, havia uma abordagem usando SQL para construir
//  a consulta com base nos campos nome e cpf do filtro. No entanto, no código final, a
//  implementação usando o Example do Spring Data JPA foi escolhida.

