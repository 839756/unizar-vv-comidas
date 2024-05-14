Feature: La aplicación permite crear un plato introduciendo sus campos esenciales

  Se añade un nuevo plato cuando el usuario
  entra en el menu de platos y pulsa crear
  plato introduciendo todos los elementos necesarios

  Scenario: Crear un plato
    Given Hay 0 platos en la aplicación
    When Añadir un plato
    Then Hay 1 platos nuevos en la aplicación