static uchar getFASTPixel(uchar offset, int x, int y)
{
    switch(offset) {
    case 0:
        return rsGetElementAt_uchar(grayAllocation, x , y + 3);
        break;
    case 1:
        return rsGetElementAt_uchar(grayAllocation, x + 1 , y + 3);
        break;
    case 2:
        return rsGetElementAt_uchar(grayAllocation, x + 2 , y + 2);
        break;
    case 3:
        return rsGetElementAt_uchar(grayAllocation, x + 3 , y + 1);
        break;
    case 4:
        return rsGetElementAt_uchar(grayAllocation, x + 3 , y);
        break;
    case 5:
        return rsGetElementAt_uchar(grayAllocation, x + 3 , y + -1);
        break;
    case 6:
        return rsGetElementAt_uchar(grayAllocation, x + 2 , y - 2);
        break;
    case 7:
        return rsGetElementAt_uchar(grayAllocation, x + 1 , y - 3);
        break;
    case 8:
        return rsGetElementAt_uchar(grayAllocation, x , y - 3);
        break;
    case 9:
        return rsGetElementAt_uchar(grayAllocation, x - 1 , y - 3);
        break;
    case 10:
        return rsGetElementAt_uchar(grayAllocation, x - 2 , y - 2);
        break;
    case 11:
        return rsGetElementAt_uchar(grayAllocation, x - 3 , y  - 1);
        break;
    case 12:
        return rsGetElementAt_uchar(grayAllocation, x - 3 , y );
        break;
    case 13:
        return rsGetElementAt_uchar(grayAllocation, x - 3, y + 1);
        break;
    case 14:
        return rsGetElementAt_uchar(grayAllocation, x - 2 , y + 2);
        break;
    case 15:
        return rsGetElementAt_uchar(grayAllocation, x -1, y + 3);
        break;
    // Next offsets are for Harris

    }
    return 0;

}