package Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "facade")
public class Facade {
    Acontroller a = new Acontroller();
    BController b = new BController();

    public Facade(Acontroller a, BController b) {
        this.a = a;
        this.b = b;
    }

    public
}
