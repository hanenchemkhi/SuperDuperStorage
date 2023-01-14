package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Credentials {

    private WebDriverWait webDriverWait;
    private WebDriver webDriver;

    public Credentials(WebDriverWait webDriverWait, WebDriver webDriver) {
        this.webDriverWait = webDriverWait;
        this.webDriver = webDriver;
    }

    public void addCredentials(String url, String username, String password)  {

        webDriver.findElement(By.id("nav-credentials-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));
        webDriver.findElement(By.id("add-credentials")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
        webDriver.findElement(By.id("credential-url")).sendKeys(url);
        webDriver.findElement(By.id("credential-username")).sendKeys(username);
        webDriver.findElement(By.id("credential-password")).sendKeys(password);
        webDriver.findElement(By.id("save-changes")).click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }
    public Integer getNumberOfCredentials(){
        webDriver.findElement(By.id("home-link")).click();
        return webDriver.findElement(By.xpath("//table[@id='credentialTable']/tbody")).
                findElements(By.tagName("tr")).size();
    }

    public void editCredentials(String url, String username, String password) {

        webDriver.findElement(By.id("nav-credentials-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));

        webDriver.findElement(By.id("edit-credential")).click();
        System.out.println("clicked edit credential");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
        System.out.println("Showing credentials");

        webDriver.findElement(By.id("credential-url")).clear();
        System.out.println("clear url");
        webDriver.findElement(By.id("credential-url")).sendKeys(url);
        System.out.println("changing url");
        webDriver.findElement(By.id("credential-username")).clear();
        webDriver.findElement(By.id("credential-username")).sendKeys(username);
        webDriver.findElement(By.id("credential-password")).clear();
        webDriver.findElement(By.id("credential-password")).sendKeys(password);
        webDriver.findElement(By.id("save-changes")).click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));

    }

    public void deleteCredentials() {


        webDriver.findElement(By.id("nav-credentials-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials")));

        webDriver.findElement(By.id("delete-credential")).click();
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
    }
}
