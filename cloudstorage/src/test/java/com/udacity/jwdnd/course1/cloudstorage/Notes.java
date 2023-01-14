package com.udacity.jwdnd.course1.cloudstorage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

    public class Notes {
        private WebDriverWait webDriverWait;
        private WebDriver webDriver;

        public Notes(WebDriver webDriver, WebDriverWait webDriverWait) {
            this.webDriver = webDriver;
            this.webDriverWait = webDriverWait;
        }
        public Integer getNumberOfNotes(){
            webDriver.findElement(By.id("home-link")).click();
            return webDriver.findElement(By.xpath("//table[@id='notesTable']/tbody")).
                    findElements(By.tagName("tr")).size();
        }

        public void addNotes(String title, String description){

            webDriver.findElement(By.id("nav-notes-tab")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));

            webDriver.findElement(By.id("addNote")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));

            webDriver.findElement(By.id("note-title")).sendKeys(title);
            webDriver.findElement(By.id("note-description")).sendKeys(description);
            webDriver.findElement(By.id("saveChanges")).click();

            webDriverWait.until(ExpectedConditions.titleContains("Result"));

        }

        public void editNotes(String title, String description){



            webDriver.findElement(By.id("nav-notes-tab")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));

            webDriver.findElement(By.id("editNote")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));

            webDriver.findElement(By.id("note-title")).sendKeys(title);
            webDriver.findElement(By.id("note-description")).sendKeys(description);
            webDriver.findElement(By.id("saveChanges")).click();
            System.out.println("finished editing");


            webDriverWait.until(ExpectedConditions.titleContains("Result"));
            System.out.println("finished editing");

        }
        public void deleteNotes(){


            webDriver.findElement(By.id("nav-notes-tab")).click();
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));

            webDriver.findElement(By.id("delete-note")).click();
            webDriverWait.until(ExpectedConditions.titleContains("Result"));
        }
    }


