package com.petrovskiy.mds.service.validator;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageValidation {

    /**
     * this method is responsible for checking is the page exists
     * @param pageable - Abstract interface for pagination information(number,size,sort).
     * @param totalNumber - total number of elements from db
     * lastPage = counts the last page based on the passed parameters : divides the total number
     *                    of pages by the page number. (the answer is rounded up)
     * @return  true if the page number is less than the counted lastPage
     */
    public boolean isPageExists(Pageable pageable, Long totalNumber) {
        if (pageable.getPageNumber() == 0) {
            return true;
        }
        long lastPage = (long) Math.ceil((double) totalNumber / pageable.getPageNumber());
        return pageable.getPageNumber() < lastPage;
    }
}
