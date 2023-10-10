package com.farmacloud.client.ui.widgets;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Pagination;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.SimplePager;

public class MyPagination extends Pagination
{
    public void rebuild(final SimplePager pager, int setDisplayLimitNum)
    {
        clear();

        final int displayLimitNum = setDisplayLimitNum;
        final int lineNum = pager.getPage() / displayLimitNum;
        int nowNum = pager.getPage() / displayLimitNum;
        int startPagerNum = nowNum * displayLimitNum;
        int endPagerNum = nowNum * displayLimitNum + displayLimitNum;

        if (nowNum * displayLimitNum + displayLimitNum > pager.getPageCount())
        {
            endPagerNum = pager.getPageCount();
        }

        if (pager.getPageCount() < displayLimitNum)
        {
            endPagerNum = pager.getPageCount();
        }

        if (pager.getPageCount() == 0)
        {
            return;
        }

        for (; startPagerNum < endPagerNum; startPagerNum++)
        {
            final int display = startPagerNum + 1;
            final AnchorListItem page = new AnchorListItem(String.valueOf(display));
            page.addClickHandler(new ClickHandler()
            {
                @Override
                public void onClick(final ClickEvent event)
                {
                    pager.setPage(display - 1);
                }
            });

            if (startPagerNum == pager.getPage())
            {
                page.setActive(true);
            }

            add(page);
        }

        final AnchorListItem prev = addPreviousLink();
        prev.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(final ClickEvent event)
            {
                pager.setPageStart((lineNum - 1) * pager.getPageSize() * displayLimitNum);
            }
        });
        prev.setEnabled(pager.hasPreviousPage());

        final AnchorListItem next = addNextLink();
        next.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(final ClickEvent event)
            {
                pager.setPageStart((lineNum + 1) * pager.getPageSize() * displayLimitNum);
            }
        });
        next.setEnabled(pager.hasNextPage());
    }

}