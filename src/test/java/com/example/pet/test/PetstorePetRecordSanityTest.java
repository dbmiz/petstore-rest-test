package com.example.pet.test;

import com.example.pet.core.TestTag;
import com.example.pet.entities.Category;
import com.example.pet.entities.Pet;
import com.example.pet.entities.Tag;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@TestTag(tag = "Test0")
public class PetstorePetRecordSanityTest {

    @Test
    public void petObjectTest() {
        List<String> photoUrl1 = Arrays.asList("photo name 1", "url.local/photo1.jpg", "photo name 2", "url.local/photo2.jpg");
        List<String> photoUrl2 = Arrays.asList("photo name 2", "url.local/photo2.jpg", "photo name 1", "url.local/photo1.jpg");

        List<Tag> tag1 = Arrays.asList(new Tag(1, "tag1"), new Tag(2, "tag2"));
        List<Tag> tag2 = Arrays.asList(new Tag(2, "tag2"), new Tag(1, "tag1"));

        Pet p1 = new Pet(12, new Category(2, "cat2"), "petname", photoUrl1, tag1, "A");
        Pet p2 = new Pet(13, new Category(2, "cat2"), "petname", photoUrl2, tag2, "A");

        Assert.assertEquals(p1, p2);
    }
}
