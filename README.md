SteelPulse-Java

SteelPulse-Java é uma aplicação desenvolvida em Java utilizando o framework Quarkus, que tem como objetivo o gerenciamento de dados de pacientes e contratos de planos de saúde. O sistema permite realizar operações de criação, consulta e atualização tanto de pacientes quanto dos contratos associados a eles.

O que foi feito

A aplicação foi construída para realizar a integração com o banco de dados Oracle e foi desenvolvida com uma estrutura baseada em JDBC para persistência de dados. As funcionalidades principais incluem a criação de pacientes, onde informações como nome, CPF, telefone e endereço são armazenadas, além de permitir a criação de contratos de planos de saúde e o gerenciamento de exames associados.

Além disso, foi implementada uma camada de repositório para persistir e consultar os dados de pacientes e contratos, utilizando a arquitetura Repository. Também foram criados testes automatizados com o uso de JUnit 5 para garantir a qualidade do código.

Funcionalidades

Cadastro de Pacientes: A aplicação permite o cadastro de pacientes, incluindo dados pessoais como nome, CPF, email e endereço.

Gestão de Contratos: Cada paciente pode ter um contrato com um plano de saúde, que também pode ser consultado.

Consulta de Pacientes e Contratos: Através do CPF do paciente, é possível buscar os dados cadastrados e seus contratos.

O projeto está configurado para ser escalável, permitindo a adição de novas funcionalidades no futuro, como a implementação de mais tipos de exames e planos de saúde.