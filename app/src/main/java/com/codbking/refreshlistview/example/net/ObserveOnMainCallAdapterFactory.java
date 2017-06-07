package com.codbking.refreshlistview.example.net;

import android.app.ProgressDialog;
import android.content.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;


/**
 * Created by wulang on 2017/4/27.
 */

public class ObserveOnMainCallAdapterFactory extends CallAdapter.Factory {
    private static final String TAG = "ObserveOnMainCallAdapte";

    private Context context;
    private String message;
    private static ProgressDialog mDialog;

    public ObserveOnMainCallAdapterFactory() {
    }

    public ObserveOnMainCallAdapterFactory(Context context, String message) {
        this.context = context;
        this.message = message;
        initDialog();
    }

    private void initDialog() {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage(message);
        mDialog.show();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(returnType) != Observable.class) {
            return null;
        }

        final CallAdapter<Object, Observable<Object>> delegate =
                (CallAdapter<Object, Observable<Object>>) retrofit.nextCallAdapter(this, returnType,
                        annotations);

        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(final Call<Object> call) {

                Observable<Object> o = delegate.adapt(call);

                Object obj = o.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .lift(new ObservableOperator<Object, Object>() {

                            public Observer<Object> apply(final Observer<Object> observer) throws Exception {

                                Observer<Object> temp = new Observer<Object>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        observer.onSubscribe(d);
                                    }

                                    @Override
                                    public void onNext(Object httpBean) {
                                        if(httpBean instanceof  HttpBean) {
                                            HttpBean bean = (HttpBean) httpBean;

                                            HttpBean.Error error = bean.getError();


                                           if (error.getNo()==NetStatus.CODE_SUCCESS) {
                                                observer.onNext(httpBean);
                                            } else {
                                                NetThrowable mThrowable = new NetThrowable(error.getNo(), error.getInfo());
                                                observer.onError(mThrowable);
                                            }
                                        }else {
                                            observer.onNext(httpBean);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        try {
                                            observer.onError(e);
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onComplete() {
                                        if (mDialog != null) {
                                            mDialog.dismiss();
                                        }
                                        observer.onComplete();
                                    }
                                };
                                return temp;
                            }
                        });

                return obj;
            }

        };
    }


}
