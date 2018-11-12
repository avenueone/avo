package org.avenue1.avo.cucumber.stepdefs;

import org.avenue1.avo.AvoApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AvoApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
