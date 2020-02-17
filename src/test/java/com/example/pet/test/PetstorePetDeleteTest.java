package com.example.pet.test;

import com.example.pet.core.PetstoreCRUDHelper;
import com.example.pet.core.TestTag;
import com.example.pet.entities.Pet;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@TestTag(tag = "Test2")
public class PetstorePetDeleteTest {
    private PetstoreCRUDHelper crudHelper = new PetstoreCRUDHelper();

    private List<Pet> newValidPets = new LinkedList<>();

    @Before
    public void setUp() {
        newValidPets.addAll(crudHelper.createValidPetRecords());
    }

    @After
    public void tearDown() {
        crudHelper.cleanUp();
    }

    @Test
    public void whenDeleteExistingPetRecord_PetIsRemoved() {
        Pet submittedPet = crudHelper.submitPetAndExtractFromResponse(newValidPets.get(0));
        deleteExistingPetAndValidateDeletion(submittedPet);
    }

    @Test
    public void whenDeleteNonExistingPetRecord_responseWithError() {
        Pet submittedPet = crudHelper.submitPetAndExtractFromResponse(newValidPets.get(0));
        deleteExistingPetAndValidateDeletion(submittedPet);

        deleteNonExistingPet(submittedPet);

        deleteNonExistingPet(new Pet(0, null, null, null, null, null));
        deleteNonExistingPet(new Pet(-100, null, null, null, null, null));
    }

    private void deleteExistingPetAndValidateDeletion(Pet pet) {
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(crudHelper.getBaseUrl() + crudHelper.getDeletePetUrl() + pet.getId())
        .then()
            .statusCode(200);

        given()
            .contentType(ContentType.JSON)
        .when()
            .get(crudHelper.getBaseUrl() + crudHelper.getGetPetUrl() + pet.getId())
        .then()
            .statusCode(404);
    }

    private void deleteNonExistingPet(Pet pet) {
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(crudHelper.getBaseUrl() + crudHelper.getDeletePetUrl() + pet.getId())
        .then()
            .statusCode(anyOf(equalTo(400), equalTo(404)));
    }
}
