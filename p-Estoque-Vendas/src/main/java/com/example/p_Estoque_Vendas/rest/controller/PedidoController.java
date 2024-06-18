package com.example.p_Estoque_Vendas.rest.controller;


import com.example.p_Estoque_Vendas.domain.entity.ItemPedido;
import com.example.p_Estoque_Vendas.domain.entity.Pedido;
import com.example.p_Estoque_Vendas.domain.enums.StatusPedido;
import com.example.p_Estoque_Vendas.domain.repository.UserRepository;
import com.example.p_Estoque_Vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.p_Estoque_Vendas.rest.dto.InformacaoItemPedidoDTO;
import com.example.p_Estoque_Vendas.rest.dto.InformacoesPedidoDTO;
import com.example.p_Estoque_Vendas.rest.dto.PedidoDTO;
import com.example.p_Estoque_Vendas.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {


    private PedidoService pedidoService;
    //INTERFACE DO DIRETORIO 'service'//

    private final UserRepository userRepository;

    public PedidoController(PedidoService pedidoService, UserRepository userRepository) {
        this.pedidoService = pedidoService ;
        this.userRepository = userRepository;
    }


    @Operation(
            summary = "Save a new Pedido",
            description = "Creates a new pedido and associates it with the user identified by the JWT token.",

            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pedido created successfully",
                            content = @Content(
                                    mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                    "{\n" +
                                            "  \"id\": 0\n" +
                                            "}"
                            ))
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
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody @Valid PedidoDTO dto, JwtAuthenticationToken token) {

        Pedido pedido = pedidoService.salvar(dto, token);

        return pedido.getId();
        //Essa linha estava dando erro pois antes constava 'public Integer...' sendo que
        //o Tipo da coluna do '@Id' de 'Pedido' é do tipo Integer ('public Integer...'CORREÇÃO).
    }


    @Operation(
            description = "Get pedido by ID",
            summary = "This is a summary for pedido get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                    "{\n" +
                                    "  \"id\": 1,\n" +
                                    "  \"user\": {\n" +
                                    "    \"userId\": \"12345678-1234-1234-1234-123456789012\",\n" +
                                    "    \"username\": \"johndoe\",\n" +
                                    "    \"password\": \"password\",\n" +
                                    "    \"roles\": [\n" +
                                    "      {\n" +
                                    "        \"roleId\": 1,\n" +
                                    "        \"name\": \"ROLE_USER\"\n" +
                                    "      }\n" +
                                    "    ]\n" +
                                    "  },\n" +
                                    "  \"creationTimeStamp\": \"2024-06-04T12:34:56.789Z\",\n" +
                                    "  \"cliente\": {\n" +
                                    "    \"id\": 1,\n" +
                                    "    \"nome\": \"Jane Doe\"\n" +
                                    "  },\n" +
                                    "  \"data_pedido\": \"2024-06-04\",\n" +
                                    "  \"total\": 150.75,\n" +
                                    "  \"status\": \"PENDING\",\n" +
                                    "  \"itens\": [\n" +
                                    "    {\n" +
                                    "      \"id\": 1,\n" +
                                    "      \"produto\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"descricao\": \"Produto A\",\n" +
                                    "        \"preco\": 50.25\n" +
                                    "      },\n" +
                                    "      \"quantidade\": 2,\n" +
                                    "      \"preco\": 100.50\n" +
                                    "    },\n" +
                                    "    {\n" +
                                    "      \"id\": 2,\n" +
                                    "      \"produto\": {\n" +
                                    "        \"id\": 2,\n" +
                                    "        \"descricao\": \"Produto B\",\n" +
                                    "        \"preco\": 25.125\n" +
                                    "      },\n" +
                                    "      \"quantidade\": 2,\n" +
                                    "      \"preco\": 50.25\n" +
                                    "    }\n" +
                                    "  ]\n" +
                                    "}"
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "",
                            description = "Internal server error",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @GetMapping("{id}")
    public InformacoesPedidoDTO getById( @PathVariable Integer id) {
        return pedidoService
                .obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converter (Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getData_pedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }


    private List<InformacaoItemPedidoDTO> converter (List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream()
                .map( item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()).collect(Collectors.toList());
    }

    @Operation(
            description = "Modify pedido by ID",
            summary = "This is a summary for pedido path endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Example 200", value =
                                            "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"user\": {\n" +
                                            "    \"userId\": \"12345678-1234-1234-1234-123456789012\",\n" +
                                            "    \"username\": \"johndoe\",\n" +
                                            "    \"password\": \"password\",\n" +
                                            "    \"roles\": [\n" +
                                            "      {\n" +
                                            "        \"roleId\": 1,\n" +
                                            "        \"name\": \"ROLE_USER\"\n" +
                                            "      }\n" +
                                            "    ]\n" +
                                            "  },\n" +
                                            "  \"creationTimeStamp\": \"2024-06-04T12:34:56.789Z\",\n" +
                                            "  \"cliente\": {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"nome\": \"Jane Doe\"\n" +
                                            "  },\n" +
                                            "  \"data_pedido\": \"2024-06-04\",\n" +
                                            "  \"total\": 150.75,\n" +
                                            "  \"status\": \"PENDING\",\n" +
                                            "  \"itens\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 1,\n" +
                                            "      \"produto\": {\n" +
                                            "        \"id\": 1,\n" +
                                            "        \"descricao\": \"Produto A\",\n" +
                                            "        \"preco\": 50.25\n" +
                                            "      },\n" +
                                            "      \"quantidade\": 2,\n" +
                                            "      \"preco\": 100.50\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "      \"id\": 2,\n" +
                                            "      \"produto\": {\n" +
                                            "        \"id\": 2,\n" +
                                            "        \"descricao\": \"Produto B\",\n" +
                                            "        \"preco\": 25.125\n" +
                                            "      },\n" +
                                            "      \"quantidade\": 2,\n" +
                                            "      \"preco\": 50.25\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            ))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "",
                            description = "Internal server error",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401",
                            content = @Content()
                    )
            }
    )
    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus( @PathVariable Integer id ,
                              @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizacaoStatus(id, StatusPedido.valueOf(novoStatus));
    }

}



// @PostMapping:
//  Essa anotação indica que o método será acionado apenas quando houver requisições
//  HTTP do tipo POST no caminho especificado pela anotação @RequestMapping("/api/pedidos")
//  na classe.
//
// @ResponseStatus(CREATED):
//  Esta anotação define o código de status HTTP que será retornado como resposta para a
//  solicitação POST bem-sucedida. Neste caso, o código de status 201 Created é retornado,
//  indicando que a criação do recurso foi realizada com sucesso.
//
// public Integer save(@RequestBody PedidoDTO dto):
//  Este é o cabeçalho do método. Ele espera um objeto PedidoDTO como corpo da requisição
//  (@RequestBody). O método retorna um Integer, que é o ID do pedido criado.
//
// Pedido pedido = pedidoService.salvar(dto);:
//  Aqui, o método salvar do serviço pedidoService é chamado, passando o PedidoDTO
//  recebido como parâmetro. O resultado é atribuído a uma variável local chamada pedido.
//
// return pedido.getId();:
//  O ID do pedido criado é retornado como resultado do método. Isso faz sentido, pois
//  após a criação bem-sucedida de um pedido, o cliente que fez a solicitação pode
//  querer saber o ID atribuído ao novo pedido.


// --@GetMapping("{id}"):--
//  Essa anotação mapeia a solicitação HTTP GET para a URL relativa fornecida ("{id}").
//  O {id} é uma variável de caminho que indica que o método aceita um parâmetro de
//  caminho chamado id. Isso significa que você pode chamar esse ponto de extremidade com
//  URLs como /api/pedidos/1, onde 1 é o valor de id.
//
// --public InformacoesPedidoDTO getById(@PathVariable Integer id):--
//  Declaração do método getById, que aceita um parâmetro id extraído do caminho da URL.
//  A anotação @PathVariable é usada para vincular o valor do parâmetro id ao valor
//  presente na parte do caminho da URL.
//
// --return pedidoService.obterPedidoCompleto(id)
// .map(p -> converter(p)).orElseThrow(() ->
// new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));:--
//
//      *pedidoService.obterPedidoCompleto(id):
//      Chama o serviço para eobter as informações completas de um pedido com o ID
//      fornecido.
//
//      *.map(p -> converter(p)):
//      Se um pedido for encontrado, aplica a função converter para converter esse pedido
//      em um InformacoesPedidoDTO. O método map é usado porque obterPedidoCompleto
//      retorna um Optional<Pedido>, e map permite manipular o valor dentro do Optional
//      se ele estiver presente.
//
//      *.orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
//      "Pedido não encontrado.")):
//      Se não houver um pedido com o ID fornecido, lança uma exceção
//      ResponseStatusException com status NOT_FOUND (404) e a mensagem "Pedido não
//      encontrado."
//
// --converter(p):--
//  Este método converte um objeto Pedido em um objeto InformacoesPedidoDTO. O código
//  específico do método converter não está visível aqui, mas, com base em outros
//  trechos de código que você compartilhou, sabemos que ele realiza essa conversão.




//private InformacoesPedidoDTO converter(Pedido pedido):--
//
//  * Este método converte um objeto Pedido para um objeto InformacoesPedidoDTO.
//
//  * Utiliza o padrão de design Builder (InformacoesPedidoDTO.builder()) para criar uma
//  instância de InformacoesPedidoDTO.
//
//  * Mapeia diferentes propriedades do objeto Pedido para as propriedades
//  correspondentes no DTO.
//
//
//codigo(pedido.getId()):--
//
//  * Define o código do pedido.
//
//
//dataPedido(pedido.getData_pedido()
// .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))):--
//
//  * Formata a data do pedido como uma string no formato "dd/MM/yyyy".
//
//
//cpf(pedido.getCliente().getCpf()):--
//
//  * Obtém o CPF do cliente associado ao pedido.
//
//
//nomeCliente(pedido.getCliente().getNome()):--
//
//  * Obtém o nome do cliente associado ao pedido.
//
//
//total(pedido.getTotal()):--
//
//  * Obtém o total do pedido.
//
//
//status(pedido.getStatus().name()):--
//
//  * Obtém o nome do status do pedido como uma string.
//
//
//items(converter(pedido.getItens())):--
//
//  * Converte a lista de itens do pedido utilizando o segundo método converter
//  definido na classe.



//private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens):--
//
//  * Este método converte uma lista de objetos ItemPedido para uma lista de objetos
//  InformacaoItemPedidoDTO.
//
//  * Verifica se a lista de itens está vazia usando CollectionUtils.isEmpty(itens).
//  Se estiver vazia, retorna uma lista vazia.
//
//  * Caso contrário, utiliza o Java Stream API para mapear cada ItemPedido para um
//  InformacaoItemPedidoDTO.
//
//
//descricaoProduto(item.getProduto().getDescricao()):--
//
//  * Obtém a descrição do produto associado ao item.
//
//
//precoUnitario(item.getProduto().getPreco()):--
//
//  * Obtém o preço unitário do produto associado ao item.
//
//
//quantidade(item.getQuantidade()):--
//
//  * Obtém a quantidade do item.
//
//  * Cria uma instância de InformacaoItemPedidoDTO utilizando o padrão de design
//  Builder.
//
//  * Coleta os resultados em uma lista utilizando collect(Collectors.toList()).




//@PatchMapping("{id}"):
//
//  Esta anotação do Spring indica que este método deve ser invocado para requisições
//  HTTP PATCH na URL especificada (/api/pedidos/{id}), onde {id} é uma variável de
//  caminho (path variable) que será capturada e fornecida como argumento para o método.
//
//
//@ResponseStatus(NO_CONTENT):
//
//  Indica que, em caso de sucesso, a resposta HTTP será 204 No Content. Isso é comum para
//  operações de atualização bem-sucedidas sem conteúdo a ser retornado.
//
//
//public void updateStatus
// (@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto):
//
//  --O método updateStatus aceita dois parâmetros:--
//
//  *@PathVariable Integer id:
//      Representa o identificador do pedido, que é extraído da URL.
//
//  *@RequestBody AtualizacaoStatusPedidoDTO dto:
//      Representa o corpo da requisição, contendo informações necessárias para a
//      atualização do status do pedido.
//
//  * Dentro do método, é extraído o novo status do DTO através de
//  String novoStatus = dto.getNovoStatus();.
//
//
//pedidoService.atualizacaoStatus(id, StatusPedido.valueOf(novoStatus));:
//
//  Chama o método atualizacaoStatus do serviço pedidoService, passando o id do pedido e
//  o novo status convertido de String para o tipo enumerado StatusPedido. Esse método
//  provavelmente realiza a lógica de negócios associada à atualização do status do
//  pedido.
//
//
// Resumindo, este método é responsável por receber uma requisição PATCH para atualizar
// o status de um pedido específico. Ele extrai o novo status do corpo da requisição e
// o repassa para o serviço responsável por efetuar a atualização. A resposta HTTP
// indicará o sucesso da operação com o código 204 No Content.







// A escolha entre os métodos HTTP PUT e PATCH depende da semântica da operação que está
// sendo realizada. Ambos são utilizados para atualizar recursos em um servidor, mas há
// diferenças na forma como eles tratam as atualizações.
//
// PUT: Geralmente é utilizado para realizar uma atualização completa do recurso. Isso
// significa que ao enviar uma requisição PUT, você está substituindo completamente o
// recurso na URL especificada pelos dados fornecidos na requisição. Se algum campo não
// for enviado, ele pode ser interpretado como nulo ou vazio, dependendo da
// implementação. Portanto, para usar PUT, você normalmente precisaria enviar todos os
// dados do recurso, mesmo que não tenham sido modificados.
//
// PATCH: É mais apropriado quando você deseja aplicar uma atualização parcial em um
// recurso. Em vez de substituir completamente o recurso, o PATCH permite enviar apenas
// os campos que precisam ser atualizados. Isso é útil quando você deseja alterar
// apenas uma parte do recurso sem afetar o restante.
//
// No seu caso, o método @PatchMapping está sendo usado para atualizar o status de um
// pedido. Como atualizar o status geralmente envolve apenas uma parte do recurso
// (o status), o método PATCH é mais semântico para essa operação. Além disso, o uso de
// PATCH reflete uma abordagem mais RESTful quando você está fazendo atualizações
// parciais em um recurso.
//
// Se você estivesse substituindo completamente o pedido, incluindo todos os detalhes,
// o uso de PUT seria mais apropriado. A escolha entre PUT e PATCH depende do contexto
// específico da sua aplicação e da natureza das operações de atualização que você está
// realizando.
