package com.fs.platform.runtime;

import java.io.File;
import java.io.FileFilter;  
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry; 
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.platform.commons.annotation.FeSaFieldInit;
import com.fs.platform.commons.annotation.FeSaService;
import com.fs.platform.runtime.rpc.IRpcUtils;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class ClassUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);
	/**
	 * 取得某个接口下所有实现这个接口的类
	 */
	public static List<Class> getAllClassByInterface(Class c) {
		List<Class> returnClassList = null;

		if (c.isInterface()) {
			// 获取当前的包名
			String packageName = c.getPackage().getName();
			// 获取当前包下以及子包下所以的类
			List<Class<?>> allClass = getClasses(packageName);
			if (allClass != null) {
				returnClassList = new ArrayList<Class>();
				for (Class classes : allClass) {
					// 判断是否是同一个接口
					if (c.isAssignableFrom(classes)) {
						// 本身不加入进去
						if (!c.equals(classes)) {
							returnClassList.add(classes);
						}
					}
				}
			}
		}
		return returnClassList;
	}

	/*
	 * 取得某一类所在包的所有类名 不含迭代
	 */
	public static String[] getPackageAllClassName(String classLocation, String packageName) {
		// 将packageName分解
		String[] packagePathSplit = packageName.split("[.]");
		String realClassLocation = classLocation;
		int packageLength = packagePathSplit.length;
		for (int i = 0; i < packageLength; i++) {
			realClassLocation = realClassLocation + File.separator + packagePathSplit[i];
		}
		File packeageDir = new File(realClassLocation);
		if (packeageDir.isDirectory()) {
			String[] allClassName = packeageDir.list();
			return allClassName;
		}
		return null;
	}

	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClasses(String packageName) {

		// 第一个class类的集合
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	public static Set<Class<?>> getClassesForSet(String pack) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		boolean recursive = true;
		String packageName = pack;
		String packageNameJar = pack;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findAndAddClassesInPackageByFileForSet(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								if (idx != -1) {
								    packageNameJar = name.substring(0, idx).replace('/', '.');
								}
								if ((idx != -1) || recursive) {
									if (name.endsWith(".class") && !entry.isDirectory()) {
										String className = name.substring(packageNameJar.length() + 1, name.length() - 6);
										try {
											classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageNameJar + '.' + className));
										} catch (ClassNotFoundException e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
			List<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					classes.add(Class.forName(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static void findAndAddClassesInPackageByFileForSet(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFileForSet(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@SafeVarargs
	public static boolean createInstances(IRpcUtils rpcUtils,Map<String, ServiceMappingEntry> serviceFieldCaches, IOnCreatedInstance cb, Set<Class<?>> toBeCreatedClasses, Map<String, Object> singletonCache, Map<String, Object>... injectables) {
		Set<Class<?>> pending = new HashSet<Class<?>>();
		for (Class<?> c : toBeCreatedClasses) {
			if (!pending.contains(c) && !singletonCache.containsKey(c.getName())) {
				scanClasses(rpcUtils,serviceFieldCaches, cb, pending, singletonCache, c, injectables[0]);
			}
		}
		handleClasses(cb, pending, singletonCache, injectables);
		return pending.isEmpty();
	}
	private static void handleClasses(IOnCreatedInstance cb, Set<Class<?>> pending, Map<String, Object> handled, Map<String, Object>... injectables) {
		while (!pending.isEmpty()) {
			Set<Class<?>> removed = new HashSet<Class<?>>();
			for (Class<?> clz : pending) {
				if (clz.getConstructors().length != 1) {
					logger.error("Multiple constructor now support now(" + clz.getName() + ")");
					return;
				}
				Constructor<?> x = clz.getConstructors()[0];
				if(x.getModifiers() != Modifier.PUBLIC || clz.getModifiers() != Modifier.PUBLIC){
					removed.add(clz);
					logger.error(clz.getName() + " must be public for class and constructor.");
					continue;
				}
				Class<?>[] params = x.getParameterTypes();
				boolean couldHandle = true;
				for (Class<?> pc : params) {
					String a = pc.getName();
					boolean couldHandleThis = false;
					couldHandleThis = handled.containsKey(a);
					if (!couldHandleThis) {
						for (Map<String, Object> injectable : injectables) {
							if (injectable.get(a) != null) {
								couldHandleThis = true;
								break;
							}
						}
					}
					if (!couldHandleThis) {
						couldHandle = false;
						if(pc.getAnnotation(FeSaService.class) == null)
							logger.error("new " + clz.getName() + " :: param (" + a + ") inject fail,Please add @FeSaService on definition.");
						break;
					}
				}
				if (couldHandle) {
					Object[] argall = new Object[params == null ? 0 : params.length];
					Class<?>[] argtypes = new Class<?>[params == null ? 0 : params.length];
					int index = 0;
					for (Class<?> pc : params) {
						String a = pc.getName();
						Object b = handled.get(a);
						if (b == null) {
							for (Map<String, Object> injectable : injectables) {
								b = injectable.get(a);
								if (b != null) {
									break;
								}
							}
						}
						argtypes[index] = pc;
						argall[index++] = b;
					}
					Object o = getNewInstance(clz, argtypes, argall, null);
					if (!cb.onCreatedInstance(clz.getName(), o)) {
						handled.put(clz.getName(), o);
					}
					removed.add(clz);
				}
			}
			if (removed.isEmpty()) {
				for (Class<?> clz : pending) {
					logger.error("ClassUtils.handleClasses dead lock left(" + clz.getName() + ")2");
				}
				return;
			}
			pending.removeAll(removed);
		}
	}
	private static void scanClasses(IRpcUtils rpcUtils,Map<String, ServiceMappingEntry> serviceFieldCaches, IOnCreatedInstance cb, Set<Class<?>> pending, Map<String, Object> handled, Class<?> clz, Map<String, Object> injectables) {
		Constructor<?> x = null;
		if (clz.getConstructors().length != 1) {
			logger.error("Zero or more than 1 public constructors not support now(" + clz.getName() + ")");
			return;
		}else{
			x = clz.getConstructors()[0];
		}
		Class<?>[] params = x.getParameterTypes();
		if (params.length == 0) {
			ServiceMappingEntry entry = serviceFieldCaches.get(clz.getName());
			if (entry == null) {
				entry = new ServiceMappingEntry();
			}
			if (checkFeSaServiceInterface(clz, entry)) {
				Set<Field> serviceFields = entry.getServiceFields();
				Field[] fields = clz.getDeclaredFields();
				for (Field field : fields) {
					Annotation annotation = field.getAnnotation(FeSaFieldInit.class);
					if (annotation != null) {
						if (serviceFields == null) {
							serviceFields = new HashSet<Field>();
						}
						serviceFields.add(field);
					} else {
						
					}
				}
				entry.setServiceFields(serviceFields);
				serviceFieldCaches.put(clz.getName(), entry);
			} else {
				
			}
			if (handled.containsKey(clz.getName())) {
				logger.error("ClassUtils.scanClasses(" + clz.getName() + ")0");
			} else {
					Object o = getNewInstance(clz, null, null,null);
					if (!cb.onCreatedInstance(clz.getName(), o)) {
						handled.put(clz.getName(), o);
					}
			}
		} else {
			if (pending.contains(clz)) {
				logger.error("ClassUtils.scanClasses(" + clz.getName() + ")1");
			} else {
				pending.add(clz);
				for (Class<?> pc : params) {
					if (!pc.isInterface() && !handled.containsKey(pc.getName()) && !pending.contains(pc)) {
						scanClasses(rpcUtils,serviceFieldCaches, cb, pending, handled, pc, injectables);
					}else{
					    if(pc.isInterface()){
					        rpcUtils.newInstance(pc);
					    }
					}
				}
			}
		}
	}
	
	public static Properties[] scanProperties(String name) {
		List<Properties> r = new LinkedList<Properties>();
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(name);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					Properties p = new Properties();
					p.load(url.openStream());
					r.add(p);
					// logger.info("file: {}", filePath);
				} else if ("jar".equals(protocol)) {
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							if (entry.getName().equals(name)) {
								Properties p = new Properties();
								p.load(jar.getInputStream(entry));
								r.add(p);
								// logger.info("jar: {}", name);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r.toArray(new Properties[0]);
	}
	public static Object getNewInstance(Class<?> clz, Class<?>[] types, Object[] args, Object... others){
		Object obj = null;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clz);
		enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE,new DbServiceInterceptor()});
		enhancer.setCallbackFilter(FeSaCallbackFilter.getInstance());
		if(args != null && types != null){
			obj = enhancer.create(types, args);
		}else{
			obj = enhancer.create();
		}
		return obj;
	}
	/**
	 * 检查是否为FeSaService对象服务类
	 * @param clazz
	 * @return
	 */
	public static boolean checkFeSaServiceInterface(Class<?> clazz, ServiceMappingEntry entry) {
		Class<?>[] interfaces1stLvl = clazz.getInterfaces();
		for (Class<?> interface1stLvl : interfaces1stLvl) {
			Class<?>[] interfaces2ndLvl = interface1stLvl.getInterfaces();
			if (interfaces2ndLvl != null && interfaces2ndLvl.length > 0) {
				if (checkFeSaServiceInterface(interface1stLvl, entry)) {
					return true;
				}
			} else {
				FeSaService anno = interface1stLvl.getAnnotation(FeSaService.class);
				if (anno != null) {
					entry.setInterfaceClass(interface1stLvl);
					// 设置HbecService别名
					if (anno.serviceName() != null && anno.serviceName().trim().length() > 0) {
						entry.setServiceName(anno.serviceName());
					}
					return true;
				}
			}
		}
		return false;
	}
}
