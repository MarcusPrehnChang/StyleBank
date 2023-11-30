Feature: Like
  like clothing items
  @test-feature
  Scenario Outline:
    Given the user is on the clothing app,
    When the user clicks on the like button
    Then the clothing item is liked

    Examples:
