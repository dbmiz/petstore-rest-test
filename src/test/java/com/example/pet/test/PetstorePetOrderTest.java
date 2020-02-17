package com.example.pet.test;

import com.example.pet.core.PetstoreCRUDHelper;
import com.example.pet.core.TestTag;
import com.example.pet.entities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;

@TestTag(tag = "Test3")
public class PetstorePetOrderTest {

    private PetstoreCRUDHelper crudHelper = new PetstoreCRUDHelper();

    @After
    public void tearDown() {
        crudHelper.cleanUp();
    }

    @Test
    public void whenPlaceOrder_responseWithPlaced() {
        Pet submittedPet = crudHelper.submitPetAndExtractFromResponse(
                new Pet(0, new Category(1, "Cat 1"), "Pet to be ordered",
                        Arrays.asList("photo.local/1.jpg"),
                        Arrays.asList(new Tag(1, "Pet tag 1")),
                        PetStatus.AVAILABLE.getStatus()));

        Order order = new Order(null, submittedPet.getId(), 1, ZonedDateTime.now().withFixedOffsetZone().toString(), "placed", null);

        Order submittedOrder = crudHelper.submitOrderAndExtractFromResponse(order);

        Assert.assertNotNull(submittedOrder.getId());
        Assert.assertEquals(order.getPetId(), submittedOrder.getPetId());
        Assert.assertEquals(OrderStatus.PLACED.getStatus(), submittedOrder.getStatus());
    }

}
