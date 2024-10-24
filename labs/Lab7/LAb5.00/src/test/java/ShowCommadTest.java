/*import org.example.CollectionManager;
import org.example.commands.realize.ShowCommand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShowCommandTest {
    private CollectionManager collectionManager;
    private ShowCommand showCommand;

    @Before
    public void setUp() {
        collectionManager = new CollectionManager("test_routes.json"); // Путь к тестовому JSON
        showCommand = new ShowCommand(collectionManager);
    }

    @Test
    public void testShowCommandWithEmptyCollection() {
        assertEquals("Коллекция пуста.", showCommand.execute(new String[]{}));
    }

    @Test
    public void testShowCommandWithNonEmptyCollection() {
        collectionManager.addRoute(new Route(1, "Route 1", new Coordinates(0, 0), ZonedDateTime.now(), null, null, 100));
        String expectedOutput = "Все маршруты в коллекции:\nRoute{id=1, name='Route 1', coordinates=Coordinates{x=0, y=0}, creationDate=" + ZonedDateTime.now() + ", from=null, to=null, distance=100}";
        assertEquals(expectedOutput, showCommand.execute(new String[]{}));
    }
}
*/
