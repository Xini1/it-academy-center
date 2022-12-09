module center.homework {
    requires java.net.http;
    requires center.shared;
    requires com.google.gson;
    opens by.itacademy.center.homework to com.google.gson;
}