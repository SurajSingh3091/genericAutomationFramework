package StepDefinition;


import ApiPOJOClasses.AddProduct;
import ApiPOJOClasses.Data;
import FrameworkUtility.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class API_StepDefinition {
    String response;

    Response res;
    JsonPath jsonPath;
    String jiraId;

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

    // Jira API
    // Authenticating using Bearer Token created using Basic Auth
    @Given("User does the pre setup for authentication to create bug")
    public void userDoesThePreSetupForAuthenticationToCreateBug() {
        RestAssured.baseURI = "https://surajsingh855190.atlassian.net/";

    }

    @When("User Creates the bug and validates if it is created and then extracts the Id")
    public void userCreatesTheBugAndValidatesIfItIsCreatedAndThenExtractsTheId() {
        response = given().header("Content-Type", "application/json")
                .header("Authorization", "Basic c3VyYWpzaW5naDg1NTE5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBFbVlVQTNvU1ViZGJvZmt5VW1Dd3hxajlKcGZNV0dGX2NrM1dPOTdRalNMeXlJay1FNUUtVEpIMHAweHNiNWJRaThhRHBiZ2hVRUQ3Y204UTdHOEFuYVQtcDQ3SnRGY0VrVGI4cGlDdHcyTmdiaGwwR042OU9BdDdack4ySnhlU1luM0pMdHMydUVrN1NFcGdVdVdMLXhKNkM5LTd1Ny05aV84OXY2TlM1OVU9MDQ2OTExQTc=")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"page not found\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();
        jsonPath = new JsonPath(response);
        jiraId = jsonPath.getString("id");
        System.out.println(jiraId);
    }

    @Then("User does pre setup for authentication to attach the bug")
    public void userDoesPreSetupForAuthenticationToAttachTheBug() {
        given().header("Authorization", "Basic c3VyYWpzaW5naDg1NTE5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBFbVlVQTNvU1ViZGJvZmt5VW1Dd3hxajlKcGZNV0dGX2NrM1dPOTdRalNMeXlJay1FNUUtVEpIMHAweHNiNWJRaThhRHBiZ2hVRUQ3Y204UTdHOEFuYVQtcDQ3SnRGY0VrVGI4cGlDdHcyTmdiaGwwR042OU9BdDdack4ySnhlU1luM0pMdHMydUVrN1NFcGdVdVdMLXhKNkM5LTd1Ny05aV84OXY2TlM1OVU9MDQ2OTExQTc=")
                .header("X-Atlassian-Token", "no-check")
                .pathParam("key", jiraId)//path param, the key in post call will be updated with the value
                .multiPart("file", new File("C:/Users/surajsingh02/OneDrive - Nagarro/Desktop/Islamic Banking.png"))
                .post("rest/api/3/issue/{key}/attachments")
                .then().assertThat().statusCode(200);
    }

    @And("validates that the attachment is success.")
    public void validatesThatTheAttachmentIssuccess() {
        response = given().header("Authorization", "Basic c3VyYWpzaW5naDg1NTE5QGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBFbVlVQTNvU1ViZGJvZmt5VW1Dd3hxajlKcGZNV0dGX2NrM1dPOTdRalNMeXlJay1FNUUtVEpIMHAweHNiNWJRaThhRHBiZ2hVRUQ3Y204UTdHOEFuYVQtcDQ3SnRGY0VrVGI4cGlDdHcyTmdiaGwwR042OU9BdDdack4ySnhlU1luM0pMdHMydUVrN1NFcGdVdVdMLXhKNkM5LTd1Ny05aV84OXY2TlM1OVU9MDQ2OTExQTc=")
                .header("Accept", "application/json")
                .pathParam("key", jiraId)
                .when().get("rest/api/3/issue/{key}")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        jsonPath = new JsonPath(response);
        String retrivedId = jsonPath.getString("sub-tasks.id");
        System.out.println(retrivedId);

    }

    @Given("^user does the pre setup with end Point url as \"([^\"]*)\"$")
    public void user_does_the_pre_setup(String endPoint) {
        RestAssured.baseURI = endPoint;
    }

    AddProduct addProduct = new AddProduct();

    @When("^user sets the input payload name as \"([^\"]*)\"$")
    public void user_sets_the_input_payload_name_as(String name) {
        addProduct.setName(name);
    }

    Data data = new Data();

    @Then("^user sets the specification Data year as \"([^\"]*)\" price as \"([^\"]*)\" model as \"([^\"]*)\" disk size as \"([^\"]*)\"$")
    public void user_sets_the_specification_data_year_as_price_as_model_as_disk_size_as(int year, double price, String model, String diskSize) {
        data.setYear(year);
        data.setPrice(price);
        data.setCPUmodel(model);
        data.setHardDiskSize(diskSize);
        addProduct.setData(data);
    }

    @Then("^user posts the payload with Path Param as \"([^\"]*)\" and extracts the response$")
    public void user_posts_the_payload_and_extracts_the_response(String pathParam) {
        String response = given().log().all()
                .header("Content-Type","application/json")
                .body(addProduct)
                .when().post(pathParam)
                .then().assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonPath jsp= new JsonPath(response);
        System.out.println("ID: "+jsp.getString("id"));
        System.out.println("Name: "+jsp.getString("name"));
        System.out.println("Created Date: "+jsp.getString("createdAt"));

    }


}

