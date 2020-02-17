package com.example.pet.entities;

import java.util.*;

public class Pet implements Comparable<Pet>, DeletableByIdRecord {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet() {
    }

    public Pet(long id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (!Objects.deepEquals(getCategory(), pet.getCategory())) return false;
        if (getName() != null ? !getName().equals(pet.getName()) : pet.getName() != null) return false;

        List<Tag> o1Tags = this.getTags();
        List<Tag> o2Tags = pet.getTags();

        if (o1Tags != null && o2Tags != null) {
            Collections.sort(o1Tags);
            Collections.sort(o2Tags);

            if (!Arrays.deepEquals(o1Tags.toArray(), o2Tags.toArray())) return false;
        }

        List<String> o1PhotoUrls = this.getPhotoUrls();
        List<String> o2PhotoUrls = pet.getPhotoUrls();

        if (o1PhotoUrls != null && o2PhotoUrls != null) {
            Collections.sort(o1PhotoUrls);
            Collections.sort(o2PhotoUrls);

            if (!Arrays.deepEquals(o1PhotoUrls.toArray(), o2PhotoUrls.toArray())) return false;
        }

        return getStatus() != null ? getStatus().equals(pet.getStatus()) : pet.getStatus() == null;
    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhotoUrls() != null ? getPhotoUrls().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Pet o) {

        if (!this.getName().equals(o.getName())) return -1;
        if (!this.getStatus().equals(o.getStatus())) return -1;

        List<Tag> o1Tags = this.getTags();
        Collections.sort(o1Tags, Comparator.comparing(Tag::getId).thenComparing(Tag::getName));
        List<Tag> o2Tags = o.getTags();
        Collections.sort(o2Tags, Comparator.comparing(Tag::getId).thenComparing(Tag::getName));

        if (!Arrays.deepEquals(o1Tags.toArray(), o2Tags.toArray())) return -1;

        List<String> o1PhotoUrls = this.getPhotoUrls();
        Collections.sort(o1PhotoUrls);
        List<String> o2PhotoUrls = o.getPhotoUrls();
        Collections.sort(o2PhotoUrls);

        if (!Arrays.deepEquals(o1PhotoUrls.toArray(), o2PhotoUrls.toArray())) return -1;

        return 0;
    }
}
