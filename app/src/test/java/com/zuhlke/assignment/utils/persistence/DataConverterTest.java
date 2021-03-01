package com.zuhlke.assignment.utils.persistence;

import com.google.gson.Gson;
import com.zuhlke.assignment.model.ImageMetadata;
import com.zuhlke.assignment.model.Location;
import com.zuhlke.assignment.persistence.DataConverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
public class DataConverterTest {

    @Test
    public void fromLocationNullTest() {
        DataConverter converter = new DataConverter();
        Location location = null;
        Object result = converter.fromLocation(location);
        assertNull(result);
    }

    @Test
    public void fromLocationTest() {
        Location location = mock(Location.class);
        DataConverter dataConverter = new DataConverter();
        Object result = dataConverter.fromLocation(location);
        assertTrue(result instanceof String);
    }

    @Test
    public void toLocationNullTest() {
        String input = null;
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toLocation(input);
        assertNull(output);
    }

    @Test
    public void testToLocationTest() {
        String input = new Gson().toJson(new Location());
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toLocation(input);
        assertTrue(output instanceof Location);
    }

    @Test
    public void testFromImageMetadataNull() {
        ImageMetadata imageMetadata = null;
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.fromImageMetadata(imageMetadata);
        assertNull(output);
    }

    @Test
    public void testFromImageMetadata() {
        ImageMetadata imageMetadata = mock(ImageMetadata.class);
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.fromImageMetadata(imageMetadata);
        assertTrue(output instanceof String);
    }

    @Test
    public void testToImageMetadataNull() {
        String input = null;
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toImageMetadata(input);
        assertNull(output);
    }

    @Test
    public void testToImageMetadata() {
        String input = new Gson().toJson(new ImageMetadata());
        DataConverter dataConverter = new DataConverter();
        Object output = dataConverter.toImageMetadata(input);
        assertTrue(output instanceof ImageMetadata);
    }
}


