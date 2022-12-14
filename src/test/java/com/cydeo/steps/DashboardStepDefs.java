package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class DashboardStepDefs
{
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;
    LoginPage loginPage=new LoginPage();
    DashBoardPage dashBoardPage=new DashBoardPage();


    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        loginPage.login(user);
         BrowserUtil.waitFor(4);
    }
    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);

    }


    @Then("the informations should be same with database")
    public void the_informations_should_be_same_with_database() {

        //1. Make a connection

        //DB_Util.createConnection(); --> We added @Before in Hooks to create conn

            //USERS

        //2. Run a query
        DB_Util.runQuery("select count(*) from users;");

        //3. Store data
        String expectedUsers = DB_Util.getFirstRowFirstColumn();

        //4. Make an assertion
        Assert.assertEquals(expectedUsers,actualUserNumbers);

             //BOOKS


    //2. Run a query
        DB_Util.runQuery("select  count(*) from books;");
    //3. Store data
        String expectedBoooks =DB_Util.getFirstRowFirstColumn();
    //4. Make an assertion
        Assert.assertEquals(expectedBoooks,actualBookNumbers);

        //BORROWED BOOKS

        //2. Run a query
        DB_Util.runQuery("select  count(*) from book_borrow\n" +
                "where is_returned=0;");
        //3. Store data
        String expectedBorrowedBooks =DB_Util.getFirstRowFirstColumn();
        //4. Make an assertion
        Assert.assertEquals(expectedBorrowedBooks,actualBorrowedBookNumbers);


        //5. Close a connection

        //DB_Util.destroy(); --> We created @After in hooks to bi run and destroy conn

    }


}
