package maria;

import com.maria.planet.Planet;
import com.maria.planet.PlanetCrudService;
import com.maria.utils.InvalidIdException;
import com.maria.utils.InvalidNameException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.maria.planet.PlanetCrudService.planetIdValidation;
import static com.maria.planet.PlanetCrudService.planetNameValidation;
import static org.junit.jupiter.api.Assertions.*;

class PlanetCrudServiceTest {
    @Test
    void testThatPlanetCreatedCorrectly() throws InvalidNameException, InvalidIdException {
        Planet newPlanet = new Planet();
        newPlanet.setId("LIL3200");
        newPlanet.setName("Lilith");
        PlanetCrudService.create(newPlanet);

        assertNotNull(newPlanet);
    }

    @Test
    void testGetByIdCorrect() throws InvalidIdException, InvalidNameException {
        Planet newPlanet = new Planet();
        newPlanet.setId("DIO1272");
        newPlanet.setName("Diona");
        PlanetCrudService.create(newPlanet);
        String id = "DIO1272";
        Planet planet = PlanetCrudService.getById(id);
        assertNotNull(planet);
    }

    @Test
    void testUpdateNameWorksCorrect() throws InvalidNameException, InvalidIdException {
        Planet newPlanet = new Planet();
        newPlanet.setId("TTN5150");
        newPlanet.setName("Titan");
        PlanetCrudService.create(newPlanet);
        String newName = "Europa";

        PlanetCrudService.updateName("TTN5150", newName);
        assertEquals(newName, PlanetCrudService.getById("TTN5150").getName());
    }

    @Test
    void deleteById() throws InvalidIdException {
        String id = "TTN5150";
        assertDoesNotThrow(() -> PlanetCrudService.deleteById(id));
        assertNull(PlanetCrudService.getById(id));
    }

    @Test
    public void getAllTest() throws InvalidNameException, InvalidIdException {
        PlanetCrudService.clearPlanet();
        List<Planet> expectedPlanets = new ArrayList<>();

        Planet planet1 = new Planet();
        planet1.setId("TRN4338");
        planet1.setName("Triton");
        expectedPlanets.add(planet1);
        PlanetCrudService.create(planet1);

        Planet planet2 = new Planet();
        planet2.setId("MMS1272");
        planet2.setName("Mimas");
        expectedPlanets.add(planet2);
        PlanetCrudService.create(planet2);

        assertEquals(expectedPlanets, PlanetCrudService.getAll());
    }

    // Id and Name Validation
    @Test
    void testThatInvalidSmallNameThrowsException() {
        assertThrows(InvalidNameException.class, () ->
                planetNameValidation(""));
    }

    @Test
    void testThatInvalidLongNameThrowsException() {
        StringBuilder name = new StringBuilder("A2");
        for (int i = 3; i < 505; i++) name.append(i);
        String finalName = name.toString();
        assertThrows(InvalidNameException.class, () ->
                planetNameValidation(finalName));
    }

    @Test
    void testThatInvalidIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                planetIdValidation("mer*-#7"));
    }

    @Test
    void testThatInvalidTooLongIdThrowsException() {
        assertThrows(InvalidIdException.class, () ->
                planetIdValidation("VFJEHGSJHGSKGJ45"));
    }

}