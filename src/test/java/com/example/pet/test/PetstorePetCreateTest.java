package com.example.pet.test;

import com.example.pet.core.PetstoreCRUDHelper;
import com.example.pet.core.TestTag;
import com.example.pet.entities.Pet;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@TestTag(tag = "Test1")
public class PetstorePetCreateTest {

    private PetstoreCRUDHelper crudHelper = new PetstoreCRUDHelper();

    private List<Pet> newValidPets = new LinkedList<>();
    private List<String> newInvalidPets = new LinkedList<>();

    @Before
    public void setUp() {
        newValidPets.addAll(crudHelper.createValidPetRecords());
        newInvalidPets.addAll(crudHelper.createInvalidPetRecords());
    }

    @After
    public void tearDown() {
        crudHelper.cleanUp();
    }

    @Test
    public void whenAddValidPetRecord_responsesWithValidJsonPet() {
        newValidPets.forEach(this::addNewPetToStoreAndValidate);
    }

    @Test
    public void whenAddInvalidPetRecord_responseWithError() {
        newInvalidPets.forEach(this::addNewInvalidPetToStoreAndValidate);
    }

    private void addNewPetToStoreAndValidate(Pet newPet) {
        Pet submittedPet = crudHelper.submitPetAndExtractFromResponse(newPet);

        Assert.assertNotNull(submittedPet.getId());
        Assert.assertEquals(newPet, submittedPet);
    }

    private void addNewInvalidPetToStoreAndValidate(String pet) {
        given()
            .contentType(ContentType.JSON)
            .body(pet)
        .when()
            .post(crudHelper.getBaseUrl() + crudHelper.getNewPetUrl())
        .then()
            .statusCode(anyOf(equalTo(404), equalTo(405), equalTo(415), equalTo(500)));
    }

}
