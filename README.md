# GraphQL Recipe Management API

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![GraphQL Java](https://img.shields.io/badge/GraphQL%20Java-22.3-E10098.svg)](https://www.graphql-java.com/)
[![Spring GraphQL](https://img.shields.io/badge/Spring%20GraphQL-1.3.5-green.svg)](https://spring.io/projects/spring-graphql)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-success.svg)]()


A Spring Boot application implementing a GraphQL API for managing recipes.

## Features

- GraphQL API with queries, mutations, and subscriptions
- Spring Data JPA with H2 database
- DataLoader for efficient data fetching
- Real-time updates with WebSocket subscriptions
- Error handling and validation
- Interactive GraphiQL interface

## Prerequisites

- JDK 21
- Maven or Gradle

## Getting Started

### Clone the repository

```bash
git clone https://github.com/yourusername/recipe-management-api.git
cd recipe-management-api
```

### Run the application

```bash
mvn spring-boot:run
```

The application will start on port 8080.

### Access GraphiQL

Open your browser and navigate to:
```
http://localhost:8080/graphiql
```

## Sample Queries

### Get all recipes

```graphql
query {
  allRecipes {
    id
    title
    description
    category
    difficulty
  }
}
```

### Get recipe by ID

```graphql
query {
  recipeById(id: 1) {
    title
    description
    ingredients {
      name
      amount
      unit
    }
    instructions {
      stepNumber
      description
    }
  }
}
```

### Create a recipe

```graphql
mutation {
  createRecipe(input: {
    title: "Chocolate Cake",
    description: "Delicious chocolate cake",
    prepTime: 20,
    cookTime: 30,
    servings: 8,
    category: "Dessert",
    difficulty: EASY
  }) {
    id
    title
  }
}
```

## Project Structure

- `model/`: Entity classes
- `repository/`: Spring Data JPA repositories
- `controller/`: GraphQL resolvers
- `dto/`: Data transfer objects
- `service/`: Business logic
- `exception/`: Custom exception handlers
- `util/`: Utility classes
- `config/`: Configuration classes
- `resources/graphql/`: GraphQL schema

## License

This project is licensed under the MIT License - see the LICENSE file for details.
```