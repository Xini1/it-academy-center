module center.setup {
    requires center.shared;
    requires com.google.gson;
    opens by.itacademy.center.setup to com.google.gson;
}