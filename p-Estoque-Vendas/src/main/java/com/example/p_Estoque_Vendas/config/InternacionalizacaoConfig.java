package com.example.p_Estoque_Vendas.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault());

        return messageSource;
    }


    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());

        return bean;
    }
}



// @Bean:
//
//  Esta anotação é usada para indicar ao Spring que o método messageSource() é responsável
//  por criar e configurar um bean. O Spring gerenciará esse bean e o disponibilizará para
//  injeção de dependência em outras partes do aplicativo.
//
//
// [public MessageSource messageSource():]
//
//  Este é o cabeçalho do método. Ele indica que o método retorna um objeto do tipo
//  MessageSource, que é uma interface do Spring usada para resolver mensagens
//  internacionalizadas.
//
//
// [ReloadableResourceBundleMessageSource messageSource =
//   new ReloadableResourceBundleMessageSource():]
//
//  Aqui, estamos criando uma instância de ReloadableResourceBundleMessageSource, que é uma
//  implementação do Spring de MessageSource. Este objeto é responsável por carregar e
//  resolver mensagens de arquivos de propriedades.
//
//
// [messageSource.setBasename("classpath:messages"):]
//
//  Este método configura o caminho base para os arquivos de mensagens. O prefixo
//  classpath: indica que os arquivos de mensagens estão localizados no classpath do
//  aplicativo. O sufixo messages especifica o nome base dos arquivos de mensagens. Por
//  exemplo, se houver arquivos chamados messages_en.properties e messages_fr.properties,
//  eles serão usados para mensagens em inglês e francês, respectivamente.
//
//
// [messageSource.setDefaultEncoding("ISO-8859-1"):]
//
//  Este método define a codificação padrão para as mensagens. Neste caso, está configurado
//  para ISO-8859-1, que é uma codificação comum para caracteres latinos.
//
//
// [messageSource.setDefaultLocale(Locale.getDefault()):]
//
//  Este método define o idioma padrão para as mensagens. Locale.getDefault() retorna o
//  Locale padrão do sistema. Isso significa que as mensagens serão carregadas no idioma
//  padrão do sistema, a menos que uma Locale específica seja fornecida.
//
//
// [return messageSource:]
//
//  Por fim, o método retorna o objeto messageSource configurado. Este objeto será
//  gerenciado pelo Spring como um bean e estará disponível para uso em outras partes do
//  aplicativo, como em mensagens de validação, por exemplo.
//
// Essa configuração permite que o aplicativo carregue e resolva mensagens
// internacionalizadas a partir de arquivos de propriedades, facilitando a localização e
// adaptação do aplicativo para diferentes idiomas e locais.
//
// Esse trecho de código configura um bean do Spring chamado messageSource, que é uma
// instância de ReloadableResourceBundleMessageSource. Este bean é responsável por lidar
// com a internacionalização (i18n) no seu aplicativo, o que inclui a localização de
// arquivos de mensagens e a configuração de como as mensagens devem ser tratadas.
//
// Portanto, quando você usa o messageSource em outras partes do seu aplicativo, ele será
// capaz de localizar e fornecer mensagens no idioma apropriado, com base nas configurações
// e arquivos de mensagens definidos aqui. Essa é uma parte fundamental para tornar seu
// aplicativo adaptável a diferentes idiomas e regiões.
