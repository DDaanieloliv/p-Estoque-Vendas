package com.example.p_Estoque_Vendas.service.impl;

import com.example.p_Estoque_Vendas.domain.entity.Cliente;
import com.example.p_Estoque_Vendas.domain.entity.ItemPedido;
import com.example.p_Estoque_Vendas.domain.entity.Pedido;
import com.example.p_Estoque_Vendas.domain.entity.Produto;
import com.example.p_Estoque_Vendas.domain.enums.StatusPedido;
import com.example.p_Estoque_Vendas.domain.repository.*;
import com.example.p_Estoque_Vendas.exception.PedidoNaoEncontradoException;
import com.example.p_Estoque_Vendas.exception.RegraNegocioException;
import com.example.p_Estoque_Vendas.rest.dto.ItemPedidoDTO;
import com.example.p_Estoque_Vendas.rest.dto.PedidoDTO;
import com.example.p_Estoque_Vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceimpl implements PedidoService {

// ou seja meio que para que o controller(PedidoController) possa fazer o uso de cada
// metodo metodo que consta na interface com sucesso, metodos esses que irão tratar da
// ou ser usados para regra de negocios , é necessario que o contrato seja cumprido = e
// os metodos criados(implementados) em 'PedidoServiceimpl' para que possam ser usados.

    private final UserRepository userRepository;
    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedidos itemsPedidosRepository;



//    public PedidoServiceimpl ( Pedidos repository, Clientes clientes, Produtos produtos ){
//        this.repository = repository;
//        this.clientesRepository = clientes;
//        this.produtosRepository = produtos;
//    }

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dtos, JwtAuthenticationToken token) {
        Integer idCliente = dtos.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de Cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dtos.getTotal());
        pedido.setData_pedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        var user = userRepository.findById(UUID.fromString(token.getName()));
        pedido.setUser(user.get());

        List<ItemPedido> itemsPedido = converterItems(pedido, dtos.getItems());
        repository.save(pedido);
        itemsPedidosRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }


// O METODO 'converterItems' RECEBE UMA LISTA COMO PARAMETRO POIS 'dto.getItems()' POR
// POSSUIR( não ser por inteiro uma Lista ) UMA COLEÇÃO DE ITEMS EM SEU INTERIOR, SE
// CARACTERIZA COMO Lista.

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items."
            );
        }

        return items.stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    if (idProduto == null) {
                        throw new RegraNegocioException("ID do produto não pode ser nulo");
                    }

                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Produto com ID " + idProduto + " não encontrado"));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                })
                .collect(Collectors.toList());

//        return items
//                .stream()
//                .map( dto -> {
//                    Integer idProduto = dto.getProduto();
//                    Produto produto = produtosRepository
//                            .findById(idProduto)
//                            .orElseThrow(
//                                    () -> new RegraNegocioException
//                                            ("Código de produto inválido " + idProduto ));
//
//                    ItemPedido itemPedido = new ItemPedido();
//                    itemPedido.setQuantidade(dto.getQuantidade());
//                    itemPedido.setPedido(pedido);   //Config de RELAÇÃO ENTRE pedido e itemPdeido//
//                    itemPedido.setProduto(produto); //Config de RELAÇÃO ENTRE produto e itemPdeido//
//                    return itemPedido;
//                } ).collect(Collectors.toList());
    }



    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    // @Override:
    //  Indica que o método está substituindo um método da interface ou da classe pai.
    //  Neste caso, esse método está implementando a interface PedidoService.
    //
    // Optional<Pedido>:
    //  Este método retorna um Optional, que é um recipiente que pode ou não conter um
    //  valor não nulo. Neste caso, ele retorna um Optional de Pedido, indicando que
    //  pode ou não haver um pedido associado ao ID fornecido.
    //
    // obterPedidoCompleto(Integer id):
    //  Este método recebe um parâmetro id do tipo Integer, que representa o identificador
    //  único de um pedido.
    //
    // repository.findByIdFetchItens(id):
    //  Chama o método findByIdFetchItens do repositório repository, que provavelmente é
    //  uma extensão personalizada do método findById que busca um pedido pelo ID, mas
    //  também traz os itens associados (itens do pedido) usando uma estratégia de fetch
    //  específica. Isso pode ser útil para evitar problemas de N+1, onde uma consulta
    //  adicional seria necessária para recuperar os itens.
    //
    // Retorno:
    //  O resultado é retornado como um Optional, significando que se o pedido com o ID
    //  fornecido for encontrado, ele estará presente no Optional, caso contrário, será
    //  vazio.

    @Override
    @Transactional
    public void atualizacaoStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontradoException()  );
    }

    // @Override:
    //  Da mesma forma que no método anterior, indica que o método está substituindo um
    //  método da interface ou da classe pai.
    //
    // @Transactional:
    //  Esta anotação indica que o método será executado dentro de uma transação. Isso
    //  significa que as operações realizadas dentro deste método (como atualizações de
    //  banco de dados) serão tratadas como uma transação única.
    //
    // --void:--
    //  Este método não retorna nenhum valor (tipo void), indicando que realiza operações
    //  de atualização no banco de dados, mas não produz um resultado específico.
    //
    // --atualizacaoStatus(Integer id, StatusPedido statusPedido):--
    //  Assim como no método anterior, recebe um ID (Integer) e um StatusPedido que
    //  representa o novo status desejado para o pedido.
    //
    // --repository.findById(id):--
    //  Busca um pedido pelo ID no repositório.
    //
    // --.map(pedido -> { ... }):--
    //  Se o pedido for encontrado, o método map é chamado. Ele recebe o pedido e executa
    //  a lógica dentro do bloco. Neste caso, define o novo status do pedido conforme
    //  fornecido pelo parâmetro.
    //
    // --return repository.save(pedido);:--
    //  Após a atualização do status, o pedido é salvo de volta no banco de dados usando
    //  o método save do repositório.
    //
    // --.orElseThrow(() -> new PedidoNaoEncontradoException()):--
    //  Se o pedido não for encontrado (o Optional estiver vazio), uma exceção
    //  PedidoNaoEncontradoException é lançada.


}



// PedidoService (Interface):
//
//  * Define um contrato ou conjunto de métodos que qualquer classe que implementar esta
//  interface deve fornecer.
//
//  * Geralmente contém métodos relacionados à lógica de negócios específica para pedidos.
//
//
// PedidoServiceimpl (Classe de Implementação):
//
//  * Implementa a interface PedidoService.
//
//  * Fornece as implementações concretas para todos os métodos definidos na interface.
//
//  * Contém a lógica de negócios real para manipular pedidos.
//
//
// PedidoController:
//
//  * Utiliza PedidoService para interagir com a lógica de negócios relacionada aos pedidos.
//
//  * Ao injetar PedidoService, na verdade, está injetando uma instância da classe
//  PedidoServiceimpl (que implementa a interface).
//
//  * O controller pode chamar os métodos definidos na interface PedidoService, e as
//  implementações reais (de PedidoServiceimpl) serão executadas.
//
//
//
//  Essa abordagem facilita a manutenção e a extensibilidade do código, uma vez que o
//  PedidoController não precisa se preocupar com as implementações específicas, apenas com
//  os métodos definidos na interface. Se precisar alterar ou adicionar lógica de negócios,
//  você pode fazer isso na implementação (PedidoServiceimpl) sem afetar o restante do código.
//  Essa é uma prática comum em design orientado a interfaces em Java.




// @Service:
//
//  * Essa anotação marca a classe como um serviço no contexto do Spring. Ela é comumente
//  usada para indicar que a classe desempenha um papel de serviço ou componente dentro
//  da aplicação.
//
//
// @RequiredArgsConstructor:
//
//  * Esta anotação do Lombok gera automaticamente um construtor com argumentos para os
//  campos marcados com final. No seu caso, os campos repository, clientesRepository,
//  produtosRepository, e itemsPedidosRepository são marcados como final, e, portanto,
//  um construtor com argumentos é gerado automaticamente.
//
//
// Campos da Classe:
//
//  * Esses são os campos necessários para que a classe possa interagir com os
//  repositórios e outras dependências.
//
//
// Construtor:
//
//  * O construtor gerado automaticamente pelo Lombok, devido à anotação
//  @RequiredArgsConstructor, injeta as dependências (repositórios) necessárias para o
//  serviço. Isso é possível porque os campos são marcados com final.




// --@Override:--
//
//  * Indica que esse método está sobrescrevendo um método da interface PedidoService.
//  Isso é útil para garantir que o método na implementação esteja de acordo com a
//  assinatura da interface.
//
//
// --@Transactional:--
//
//  * Essa anotação indica que o método será executado dentro de uma transação. Em caso
//  de sucesso, as alterações serão confirmadas no banco de dados; em caso de falha, as
//  alterações serão revertidas. Isso ajuda a manter a consistência dos dados.
//
//



// --Obtendo o ID do Cliente do DTO:--
//
//  * Integer idCliente = dto.getCliente();:
//      Obtém o ID do cliente a partir do objeto PedidoDTO fornecido.
//


// --Buscando o Cliente no Repositório:--
//
//  * Cliente cliente = clientesRepository.findById(Integer.valueOf(idCliente))
//  .orElseThrow(() -> new RegraNegocioException("Código de Cliente inválido"));:
//      Busca o cliente no repositório de clientes usando o ID obtido. Caso o cliente
//      não seja encontrado, lança uma exceção indicando que o código do cliente é
//      inválido.
//



// --Criando uma Instância de Pedido:--
//
//  * Pedido pedido = new Pedido();:
//      Cria uma nova instância da classe Pedido. A criação da instância de Pedido é
//      necessária para representar um novo pedido que será persistido no sistema com
//      as informações fornecidas no PedidoDTO (Data Transfer Object).
//



// --Configurando Atributos do Pedido:--
//
//  * pedido.setTotal(dto.getTotal());:
//      Define o total do pedido com base nas informações do DTO.
//
//  * pedido.setData_pedido(LocalDate.now());:
//      Define a data do pedido como a data atual.
//
//  * pedido.setCliente(cliente);:
//      Define o cliente associado ao pedido.
//      É definida a chave esytangeira cliente na classe produto.
//



// --Convertendo Items do Pedido:--
//
//  * List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());:
//      Chama o método privado converterItems para converter os itens do DTO em uma lista
//      de ItemPedido.
//
//  * '(pedido, dto.getItems());':
//      Aqui esta sendo passado a Instância de Pedido, feita anteriormente, como parametro
//      para que se possa fazer a conversão dos ITEMS passados no DTO em uma Lista, tal
//      Lista fará parte de 'pedido'.
//
//      {  Essa abordagem permite que o método converterItems associe os itens de pedido
//      (obtidos do dto.getItems()) a esse pedido específico. }
//
//
//  --Salvando o Pedido e os Items do Pedido no Repositório:--
//
//  * repository.save(pedido);:
//      Salva o pedido no repositório.
//
//  * itemsPedidosRepository.saveAll(itemsPedido);:
//      Salva todos os itens do pedido no repositório de itens de pedidos.
//


// --Configurando os Itens no Pedido:--
//
//  * pedido.setItens(itemsPedido);:
//      Define a lista de itens do pedido no objeto Pedido.
//


// --Retornando o Pedido Persistido:--
//
//  * return pedido;:
//      Retorna o objeto Pedido que foi persistido no banco de dados.
//
//
// Em resumo, este método encapsula a lógica de negócios associada à criação e
// persistência de um novo pedido, utilizando as informações fornecidas pelo objeto
// PedidoDTO.



// Verificação de Items Vazios:
//
//  * 'if (items.isEmpty()) {...}':
//      Essa parte verifica se a lista de items está vazia. Se estiver vazia, significa
//      que não há itens no pedido, o que não é permitido. Nesse caso, uma exceção
//      RegraNegocioException é lançada, informando que não é possível realizar um
//      pedido sem itens.
//
//
// Parametros:
//
//  * 'private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items)':
//      Essa parte, sereferindo aos parametros, diz o tipo de parametros que deve ser
//      recebido pelo metodo 'converterItems' para que possa ser feita a converção.
//
//
//
// Mapeamento e Conversão dos Items:
//
// Essa parte do código usa as funcionalidades introduzidas no Java 8 por meio do pacote
// java.util.stream. Vou explicar cada parte:
//
//  * items.stream():
//  Isso converte a lista items em um stream. Um stream é uma sequência de elementos que
//  podem ser processados em paralelo ou de forma sequencial.
//
//
//  * map(dto -> { ... }):
//  O método map é usado para transformar cada elemento do stream aplicando uma função.
//  Neste caso, a função recebe um ItemPedidoDTO (dto) e retorna um ItemPedido.
//
//
//  * Integer idProduto = dto.getProduto();:
//  Obtém o ID do produto do ItemPedidoDTO.
//
//
//  * Produto produto = produtosRepository.findById(Integer.valueOf(idProduto))
//  .orElseThrow(() -> new RegraNegocioException("Código de produto inválido " +
//  idProduto));:
//
//  Usa o ID do produto para procurar o objeto Produto no repositório
//  (produtosRepository). Se não encontrar, lança uma exceção indicando que o código do
//  produto é inválido.
//
//
//  * ItemPedido itemPedido = new ItemPedido();:
//  Cria uma nova instância de ItemPedido.
//
//
//  * itemPedido.setQuantidade(dto.getQuantidade());:
//  Configura a quantidade no ItemPedido usando a quantidade do ItemPedidoDTO.
//
//
//  * itemPedido.setPedido(pedido);:
//  Associa o ItemPedido ao pedido fornecido como parâmetro.
//  Define o ID do pedido ao item que esta associado a ele(itemPedido).
//  {É definida a chave estrangeira pedido em itemPedido}.
//  Em outras palavras, diz que esse ItemPedido pertence a um determinado Pedido.
//
//
//  * itemPedido.setProduto(produto);:
//  Associa o ItemPedido ao produto encontrado.
//  Define o ID do produto ao item do pedido que faz parte da coleção(itemPeddido).
//  {É definida a chave estrangeira produto em itemPedido}.
//  Indica qual produto específico está incluído neste ItemPedido.
//
//
//  * return itemPedido;:
//  Retorna o ItemPedido criado.
//
//
//  * collect(Collectors.toList()):
//  Conclui a operação no stream e coleta os resultados em uma lista. Isso retorna a
//  lista final de ItemPedido que foi criada ao processar cada ItemPedidoDTO da lista
//  original.
//
//
//  Em resumo, essa parte do código está mapeando cada ItemPedidoDTO para um ItemPedido
//  correspondente, associando corretamente o produto e o pedido, e coletando tudo em
//  uma lista.