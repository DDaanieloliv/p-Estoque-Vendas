package com.example.p_Estoque_Vendas.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

// Definindo Algumas informações no Documento/Documentação do Swagger UI

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Daniel",
                        email = "daniel****@gmail.com",
                        url = "https://github.com/DDaanieloliv"
                ),
                description = "OpenApi documentation for Spring end spring Security",
                title = "OpenApi specification - Daniel",
                version = "1.0",
                license = @License(
                        name = "Some license name",
                        url = "https://Some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local DEV ENV",
                        url = "https://github.com/DDaanieloliv"
                ),
                @Server(
                        description = "PROD DEV ENV",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    //  http://localhost:8080/swagger-ui/index.html

    //  Alguns comandos da dumentação do Swagger UI podem causar
    //  bugs em algumas rotas http as quais estão sendo documentadas.
    //  Caso algum endPoint de ´Cliente´ esteja com maç funcionamento
    //  retire esse import da file ´ClienteController´ e seu uso da documentação,
    //  ´import io.swagger.v3.oas.annotations.media.Schema;´.

}


// _@OpenAPIDefinition_
//
// Essa anotação é usada para definir as informações gerais da API para a documentação do
// Swagger.
//
// info: Define informações sobre a API, como título, descrição, versão, informações de
//       contato e licença.
// contact: Informações de contato do responsável pela API.
// description: Descrição da API.
// title: Título da API.
// version: Versão da API.
// license: Informações sobre a licença da API.
// termsOfService: URL ou texto dos termos de serviço da API.
// servers: Define os servidores nos quais a API está hospedada.
//
//
// _Aqui, temos dois servidores:_
//
// Um servidor de desenvolvimento local com a descrição "Local DEV ENV" e URL
// "https://github.com/DDaanieloliv".
//
// Um servidor de produção com a descrição "PROD DEV ENV" e URL "http://localhost:8080".
//
// security: Define os esquemas de segurança usados pela API.
//
// @SecurityRequirement: Indica que a API requer um esquema de segurança chamado
// "bearerAuth".
//
// Isso significa que a API espera um token de autenticação JWT (Bearer Token) para
// acessar recursos protegidos.
//
//
// _@SecurityScheme_
//
// Essa anotação define um esquema de segurança chamado "bearerAuth".
//
// name: Nome do esquema de segurança.
// description: Descrição do esquema de segurança.
// scheme: Esquema de autenticação utilizado, no caso, "bearer" para Bearer Token.
// type: Tipo de esquema de segurança, neste caso, HTTP.
// bearerFormat: Formato do token de Bearer, no caso, "JWT".
// in: Localização do token no request, neste caso, no cabeçalho (HEADER).
//
//
// _public class OpenApiConfig_
//
// Esta é a classe Java que contém toda a configuração do Swagger para a documentação da API.
//
// Ela é anotada com @OpenAPIDefinition e @SecurityScheme, fornecendo as informações
// necessárias para o Swagger gerar a documentação correta.
//
// Geralmente, essa classe também teria outros métodos e configurações relacionadas ao
// Swagger, mas neste caso específico, está vazia, apenas com um comentário indicando a
// URL onde a documentação gerada pelo Swagger pode ser acessada.
//
//Resumo
//Este código Java configura o Swagger para gerar a documentação da API (OpenAPI) para uma aplicação Spring.
//Define informações gerais da API, como título, descrição, versão, contato e licença.
//Especifica os servidores nos quais a API está hospedada.
//Define um esquema de segurança Bearer Token (JWT) chamado "bearerAuth".
//Esta configuração é útil para os desenvolvedores entenderem a API e para clientes
//(como o Swagger UI) interagirem com ela de forma documentada e segura.