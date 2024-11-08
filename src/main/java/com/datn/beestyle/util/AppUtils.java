package com.datn.beestyle.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class AppUtils {

    private static final String PRODUCT_CODE_PREFIX = "SP";
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

    public static List<Integer> handleStringIdsToIntegerIdList(String StringIds) {
        List<Integer> integerIdList = null;
        String[] colorIdsStr = StringIds != null ? StringIds.split(",") : null;
        if (colorIdsStr != null) {
            integerIdList = new ArrayList<>();
            for (String strId : colorIdsStr) {
                int id;
                try {
                    id = Integer.parseInt(strId);
                } catch (Exception e) {
                    continue;
                }
                integerIdList.add(id);
            }
        }
        return integerIdList;
    }

    public static String generateProductCode(Long id) {
        return String.format("%s%06d", PRODUCT_CODE_PREFIX, id);
    }

    public static void main(String[] args) {
        System.out.println(toSlug("Đây là một ví dụ về Slug!"));
    }
}
