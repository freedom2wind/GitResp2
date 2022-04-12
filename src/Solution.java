import java.util.*;

public class Solution {

    public static void main(String[] args){
        Solution s = new Solution();
        int[] nums = new int[]{};
        System.out.println(Arrays.toString(s.searchRange(nums,0)));
    }

    public int[] searchRange(int[] nums, int target) {
        if(nums.length==0)
            return new int[]{-1,-1};
        int leftIdx = binarySearch(nums,target,true);
        int rightIdx = binarySearch(nums,target,false)-1;
        if(nums[nums.length-1]==target)
            rightIdx = nums.length-1;
        if(leftIdx<=rightIdx&&rightIdx<nums.length&&nums[leftIdx]==target&&nums[rightIdx]==target)
            return new int[]{leftIdx,rightIdx};
        else
            return new int[]{-1,-1};
    }
    public int binarySearch(int[] nums,int target,boolean lower)
    {
        int left = 0,right = nums.length-1,ans = 0;
        while(left<=right)
        {
            int mid = (left+right)/2;
            if(nums[mid]>target||(lower&&nums[mid]>=target))
            {
                right = mid-1;
                ans = mid;
            }
            else
            {
                left = mid + 1;

            }
        }
        return ans;
    }

}
