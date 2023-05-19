# GithubUsers
Neste projeto você encontra:

## Arquitetura
#### MVVM 
- Padrão de arquitetura utilizado juntamente com architecture components como ViewModel e LiveData. 
#### Domain Driven Design
- Objetos chegam das requisições como DTOs e são convertidos para seu respectivo modelo de domínio permitindo uma melhor separação entre a camada de requisição e a camada de domínio do projeto.
#### Koin 
- Framework utilizada para injeção das dependências do projeto. Facilita testes e desacoplamento.
#### Paging 3 
- Biblioteca recomendada pela Google para implementação de paginação de listas.
#### ViewBinding 
- Biblioteca que liga o xml dos layouts ao código das views.
## Requisições
#### Coroutines 
- Para lidar com a assincronicidade das operações.
#### Retrofit 
- Para lidar com as requisições http.
#### CallAdapterFactory 
- As classes comuns a todas as request onde se encontra o handle dos possíveis erros recebidos ao executar uma requisição são inseridas no Retrofit por meio de um CallAdapter.
#### Picasso 
- Para lidar com download e apresentação de imagens.
## Testes
#### Mockito
- Mock de objetos para os testes unitários. 
#### JUnit
- Para criação de testes unitários.
#### Espresso
- Permite os testes de interface do usuário.
