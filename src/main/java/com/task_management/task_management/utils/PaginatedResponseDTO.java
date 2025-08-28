package com.task_management.task_management.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDTO <T> {
    private List<T> items;
    private int currentPage;
    private long totalItems;
    private int totalPages;
}