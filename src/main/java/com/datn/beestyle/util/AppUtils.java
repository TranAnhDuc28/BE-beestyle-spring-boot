package com.datn.beestyle.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class AppUtils {

    public static final int MAX_CATEGORY_LEVEL = 3;
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITE_SPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGES_DASHES = Pattern.compile("(^-|-$)");

    public static String toSlug(String input) {
        // Thay thế khoảng trắng bằng dấu gạch ngang
        String noWhiteSpace = WHITE_SPACE.matcher(input).replaceAll("-");

        // Thay ký tự đĐ
        String str = noWhiteSpace.replaceAll("[đĐ]", "d");

        // Bỏ dấu tiếng Việt bằng cách chuẩn hóa
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);

        // Loại bỏ tất cả các ký tự không phải là chữ Latin hoặc dấu gạch ngang
        String slug = NON_LATIN.matcher(normalized).replaceAll("");

        // Loại bỏ dấu gạch ngang thừa ở đầu hoặc cuối chuỗi
        slug = EDGES_DASHES.matcher(slug).replaceAll("");

        // Chuyển đổi thành chữ thường
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static void main(String[] args) {
        System.out.println(toSlug("Đây là một ví dụ về Slug!"));
    }
}
