package StepDefinition;


import FrameworkUtility.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class API_StepDefinition {
    String response;

    Response res;
    JsonPath jsonPath;

    RestAssured restAssured;

    @Given("user had the complex JSON as response")
    public void user_had_the_complex_json_as_response() {
        response = "{\n" +
                "\n" +
                "\"dashboard\": {\n" +
                "\n" +
                "\"purchaseAmount\": 910,\n" +
                "\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" +
                "\n" +
                "},\n" +
                "\n" +
                "\"courses\": [\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"Selenium Python\",\n" +
                "\n" +
                "\"price\": 50,\n" +
                "\n" +
                "\"copies\": 6\n" +
                "\n" +
                "},\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"Cypress\",\n" +
                "\n" +
                "\"price\": 40,\n" +
                "\n" +
                "\"copies\": 4\n" +
                "\n" +
                "},\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"title\": \"RPA\",\n" +
                "\n" +
                "\"price\": 45,\n" +
                "\n" +
                "\"copies\": 10\n" +
                "\n" +
                "}\n" +
                "\n" +
                "]\n" +
                "\n" +
                "}";

        jsonPath = new JsonPath(response);
    }

    @When("user wants print No of courses returned by API")
    public void user_wants_print_no_of_courses_returned_by_api() {
        int size = jsonPath.getInt("courses.size()");
        System.out.println("Courses Size: " + size);
    }

    @Then("user Print Purchase Amount")
    public void user_print_purchase_amount() {
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount: " + purchaseAmount);
        //System.out.println(jsonPath.get("dashboard.purchaseAmount").toString());
    }

    @Then("user Print Title of the first course")
    public void user_print_title_of_the_first_course() {
        //System.out.println(jsonPath.get("courses.title[0]").toString());
        String firstTitle = jsonPath.get("courses[0].title");
        System.out.println("FirstTitle: " + firstTitle);
    }

    @Then("user Print All course titles and their respective Prices")
    public void user_print_all_course_titles_and_their_respective_prices() {
        for (int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            System.out.println(jsonPath.get("courses[" + i + "].title").toString());
            System.out.println(jsonPath.get("courses[" + i + "].price").toString());
        }
    }

    @Then("user Print no of copies sold by RPA Course")
    public void user_print_no_of_copies_sold_by_rpa_course() {
        for (int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            if (jsonPath.get("courses.title[" + i + "]").toString().equals("RPA"))
                System.out.println("Copies for RPA sold is " + jsonPath.get("courses.copies[" + i + "]").toString());
        }
    }

    @Then("finally user Verify if Sum of all Course prices matches with Purchase Amount")
    public void finally_user_verify_if_sum_of_all_course_prices_matches_with_purchase_amount() {
        int sum = 0;
        for (int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            sum += jsonPath.getInt("courses[" + i + "].price") * jsonPath.getInt("courses[" + i + "].copies");
        }
        if ((jsonPath.getInt("dashboard.purchaseAmount") == sum))
            System.out.println("Sum of Copies Sold is Equal to Purchase Amount");
        else
            System.out.println("Not equal " + sum + jsonPath.get("dashboard.purchaseAmount").toString());
    }


    //**********Library Application****************


    @Given("User does the pre setup for the library application")
    public void user_does_the_pre_setup_for_the_library_application() {
        restAssured.baseURI = BaseClass.endPointUrl;
    }

    @When("User adds the book")
    public void user_adds_the_book() {
        res = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "\"name\": \"Wander and Wonder\",\n" +
                        "\"isbn\": \"NCERT\",\n" +
                        "\"aisle\":\"31fgfred10\",\n" +
                        "\"author\":\"RandomPeople\",\n" +
                        "}")
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response();

        System.out.println(res.prettyPrint());
        System.out.println("response");
        jsonPath = res.jsonPath();
    }

    @Then("User validates that book is added successfully")
    public void user_validates_that_book_is_added_successfully() {
        String bookId = jsonPath.get("ID");
        System.out.println(bookId);
        /*given().header("Content-Type","application/json")
                .when().get()*/
    }

    @Then("Then user deletes the book.")
    public void then_user_deletes_the_book() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    //singh is King
}

