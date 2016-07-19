package org.paradise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import com.fasterxml.jackson.module.paranamer.ParanamerOnJacksonAnnotationIntrospector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 20/07/2016.
 */
public class JacksonModuleBaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String JSON = "{\"name\":\"Bob\", \"age\":40}";

    private ObjectMapper objectMapper;


    @Test
    public void testWithoutModule() throws Exception {

        thrown.expect(JsonMappingException.class);
        thrown.expectMessage("has no property name annotation");

        new ObjectMapper().readValue(JSON, CreatorBean.class);
    }

    @Test
    public void testParanamerModule() throws Exception {

        objectMapper = new ObjectMapper().registerModule(new ParanamerModule());

        CreatorBean bean = objectMapper.readValue(JSON, CreatorBean.class);

        assertEquals("Bob", bean.getName());
        assertEquals(40, bean.getAge());
    }

    @Test
    public void testParanamerOnJacksonAnnotationIntrospector() throws Exception {

        objectMapper = new ObjectMapper().setAnnotationIntrospector(new ParanamerOnJacksonAnnotationIntrospector());

        CreatorBean bean = objectMapper.readValue(JSON, CreatorBean.class);

        assertEquals("Bob", bean.getName());
        assertEquals(40, bean.getAge());
    }

}

class CreatorBean {

    private final int age;
    private final String name;

    @JsonCreator
    public CreatorBean(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}
