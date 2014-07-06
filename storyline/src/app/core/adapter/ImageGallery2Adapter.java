package app.core.adapter;
//package app.xzone.storyline.adapter;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.support.v4.util.LruCache;
//import android.support.v4.view.ViewPager.LayoutParams;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.GridView;
//import android.widget.ImageView;
//
//public class ImageGallery2Adapter extends BaseAdapter {
//	Context context;
//	ArrayList<String> items;
//	private LruCache<String, Bitmap> mMemoryCache;
//	
//	public ImageGallery2Adapter(Context context, ArrayList<String> items){
//		this.context = context;
//		this.items = items;
//		
//		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//		final int cacheSize = maxMemory / 8;
//		
//		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
//			protected int sizeOf(String key, Bitmap bitmap){
//				return bitmap.getRowBytes();
//			}
//		};
//		
//		
//	}
//	
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return items.size();
//	}
//
//	@Override
//	public Object getItem(int arg0) {
//		// TODO Auto-generated method stub
//		return items.get(arg0);
//	}
//
//	@Override
//	public long getItemId(int arg0) {
//		// TODO Auto-generated method stub
//		return arg0;
//	}
//
//	@Override
//	public View getView(int arg0, View convertView, ViewGroup arg2) {
//		// TODO Auto-generated method stub
//		ImageView img = null;
//		 
//        if (convertView == null) {
//            img = new ImageView(context);
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            img.setLayoutParams(new GridView.LayoutParams(
//                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        } else {
//            img = (ImageView) convertView;
//        }
// 
//        int resId = context.getResources().getIdentifier(items.get(arg0),
//                "drawable", context.getPackageName());
// 
//        loadBitmap(resId, img);
// 
//        return img;
//	}
//	
//	public void loadBitmap(int resId, ImageView imageView) {
//        if (cancelPotentialWork(resId, imageView)) {
//            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
//            imageView.setBackgroundResource(R.drawable.empty_photo);
//            task.execute(resId);
//        }
//    }
//	
//	static class AsyncDrawable extends BitmapDrawable {
//        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
// 
//        public AsyncDrawable(Resources res, Bitmap bitmap,
//                BitmapWorkerTask bitmapWorkerTask) {
//            super(res, bitmap);
//            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
//                    bitmapWorkerTask);
//        }
// 
//        public BitmapWorkerTask getBitmapWorkerTask() {
//            return bitmapWorkerTaskReference.get();
//        }
//    }
//	
//	
//	public static boolean cancelPotentialWork(int data, ImageView imageView) {
//        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
// 
//        if (bitmapWorkerTask != null) {
//            final int bitmapData = bitmapWorkerTask.data;
//            if (bitmapData != data) {
//                // Cancel previous task
//                bitmapWorkerTask.cancel(true);
//            } else {
//                // The same work is already in progress
//                return false;
//            }
//        }
//        // No task associated with the ImageView, or an <span id="IL_AD5" class="IL_AD">existing</span> task was
//        // cancelled
//        return true;
//    }
//	
//	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
//        if (imageView != null) {
//            final Drawable drawable = imageView.getDrawable();
//            if (drawable instanceof AsyncDrawable) {
//                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
//                return asyncDrawable.getBitmapWorkerTask();
//            }
//        }
//        return null;
//    }
//	
//	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
//        if (getBitmapFromMemCache(key) == null) {
//            mMemoryCache.put(key, bitmap);
//        }
//    }
// 
//    public Bitmap getBitmapFromMemCache(String key) {
//        return (Bitmap) mMemoryCache.get(key);
//    }
//    
//    
//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
//        public int data = 0;
//        private final WeakReference<ImageView> imageViewReference;
// 
//        public BitmapWorkerTask(ImageView imageView) {
//            // Use a WeakReference to ensure the ImageView can be garbage
//            // collected
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
// 
//        // Decode image in background.
//        @Override
//        protected Bitmap doInBackground(Integer... params) {
//            data = params[0];
//            final Bitmap bitmap = decodeSampledBitmapFromResource(
//                    context.getResources(), data, 100, 100);
//            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
//            return bitmap;
//        }
// 
//        // Once complete, see if ImageView is still around and set bitmap.
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (imageViewReference != null && bitmap != null) {
//                final ImageView imageView = imageViewReference.get();
//                if (imageView != null) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
// 
//    public static Bitmap decodeSampledBitmapFromResource(Resources res,
//            int resId, int reqWidth, int reqHeight) {
// 
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
// 
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth,
//                reqHeight);
// 
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
// 
//    public static int calculateInSampleSize(BitmapFactory.Options options,
//            int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
// 
//        if (height > reqHeight || width > reqWidth) {
// 
//            // Calculate ratios of height and width to requested height and
//            // width
//            final int heightRatio = Math.round((float) height
//                    / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
// 
//            // Choose the smallest ratio as inSampleSize value, this will
//            // guarantee
//            // a final image with both dimensions larger than or equal to the
//            // requested height and width.
//            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//        }
// 
//        return inSampleSize;
//    }
//
//}
